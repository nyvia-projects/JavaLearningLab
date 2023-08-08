import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class GaleShapleyProject {

    static int NUMBER_OF_PAIRS;

    private static class Element {
        @Override
        public String toString() {
            return id + ": " +
                    preferenceList;
        }

        private final int id;

        private final boolean isMan;
        private static int NUMBER_OF_INSTANCE_REQUESTS = 0;

        private final List<Integer> preferenceList;


        private Element(int id, boolean isMan, List<Integer> preferenceList) {
            this.id = id;
            this.isMan = isMan;
            this.preferenceList = preferenceList;
        }

        public int getId() {
            return id;
        }

        public boolean isMan() {
            return isMan;
        }

        public static Element getElement(List<Integer> preferenceList) {
            int generatedId = 1;
            boolean isMan = false;
            if (NUMBER_OF_INSTANCE_REQUESTS < NUMBER_OF_PAIRS) {
                generatedId += NUMBER_OF_INSTANCE_REQUESTS;
                isMan = true;
            } else
                generatedId += NUMBER_OF_INSTANCE_REQUESTS - NUMBER_OF_PAIRS;

            NUMBER_OF_INSTANCE_REQUESTS++;
            return new Element(generatedId, isMan, preferenceList);
        }

        public List<Integer> getPreferenceList() {
            return preferenceList;
        }

        public void removeFromPreferenceList() {
            preferenceList.remove(0);
        }

    }

    private static class StableMatchingProblem {
        private final List<Element> men;

        private final List<Element> women;

        private StableMatchingProblem(List<Element> men, List<Element> women) {
            this.men = men;
            this.women = women;
        }

        public List<Element> getMen() {
            return men;
        }

        public List<Element> getWomen() {
            return women;
        }


        public List<Integer> getMenPrefList(int id) {
            return men.stream()
                    .filter(element -> element.getId() == id)
                    .map(Element::getPreferenceList)
                    .findFirst()
                    .orElse(List.of());
        }

        public List<Integer> getWomenPrefLists(int id) {
            return men.stream()
                    .filter(element -> element.getId() == id)
                    .map(Element::getPreferenceList)
                    .findFirst()
                    .orElse(List.of());
        }

        public void popPreferenceList(int id) {
            try {
                Objects.requireNonNull(getMen().stream()
                                .filter(element -> element.getId() == id)
                                .findFirst()
                                .orElse(null))
                        .removeFromPreferenceList();
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }

        public static StableMatchingProblem getStableMatchingProblemInstance(
                List<Element> men, List<Element> women) {
            return new StableMatchingProblem(men, women);
        }

    }

    public static List<Element> readFileAndReceiveProblemInstance(String fileName) {
//        to read preference list values
        List<Element> elements = new ArrayList<>();
//        to accept first value as NUMBER_OF_PAIRS
        int lineCount = 1;
//        current line to work with
        String currentData;
        try {
            Scanner reader = new Scanner(new File(fileName));
            while (reader.hasNextLine()) {
                try {
                    currentData = reader.nextLine();
//                    reading first line is for setting NUMBER_OF_PAIRS value
                    if (lineCount == 1) {
                        NUMBER_OF_PAIRS = Integer.parseInt(currentData);
                    }
//                    other lines that contain preference lists
                    else {
//                        add (new instance of Element) with read currentData as preference list(queue) value
                        elements.add(Element.getElement(
                                currentData.chars()
                                        .boxed()
                                        .filter(Character::isDigit)
                                        .map(Character::getNumericValue)
                                        .collect(Collectors.toCollection(LinkedList::new))));
                    }
                    lineCount++;
                } catch (NumberFormatException e) {
                    System.out.println("Error with integer input from " + fileName);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading file " + fileName);
            e.printStackTrace();
        }
        // return the list of made Element objects
        return elements;
    }


    public static Map<Integer, Integer> solveStableMatchingProblem(StableMatchingProblem problem) {
//        will contain current and future pair matches
        Map<Integer, Integer> matchingRepo = new HashMap<>(NUMBER_OF_PAIRS);
//        will contain proposal history of each man | mapped manId to List of womanIds
        Map<Integer, List<Integer>> proposalRepo = new HashMap<>(NUMBER_OF_PAIRS);

        List<Integer> freeMen = new ArrayList<>(problem.getMen()
                .stream()
                .map(Element::getId)
                .collect(Collectors.toList()));

        List<Integer> freeWomen = new ArrayList<>(problem.getWomen()
                .stream()
                .map(Element::getId)
                .collect(Collectors.toList()));

//       List to swap entries in main proposalRepo
        List<Integer> currentManProposalHistory = new ArrayList<>();

        while (!freeMen.isEmpty()) {

//            get first manId in list to work with
            final int currentManId = freeMen.get(0);
//            get currentMan's prefList
            List<Integer> currentManPrefList = problem.getMenPrefList(currentManId);

//            get first womanId from preferenceList that currentMan hasn't proposed to
            int desiredWoman = currentManPrefList.get(0);
            while (currentManProposalHistory.contains(desiredWoman)) {
                problem.popPreferenceList(currentManId);
                desiredWoman = currentManPrefList.get(0);
            }

//            desiredWoman's preferenceList
            final List<Integer> womanPrefList = problem.getWomenPrefLists(desiredWoman);

//                add this proposal to currentMan proposal history
            currentManProposalHistory.add(desiredWoman);
//                update proposalRepo
            proposalRepo.put(currentManId, currentManProposalHistory);
//                if desiredWoman is free
            if (freeWomen.contains(desiredWoman)) {
//                record this matching in matchingRepo
                matchingRepo.put(currentManId, desiredWoman);

//                remove currentMan and desiredWoman from FREE Pool
                freeMen.remove((Integer) currentManId);
                freeWomen.remove((Integer) desiredWoman);
            }
//            if woman is already matched
            else if (matchingRepo.containsValue(desiredWoman)) {
//                retrieve her partner's id
                int currentDesiredWoman = desiredWoman;
                int herManId = matchingRepo.keySet()
                        .stream()
                        .filter(integer -> matchingRepo.get(integer)
                                .equals(currentDesiredWoman))
                        .findFirst()
                        .orElse(-1);
//                if desiredWoman DOES prefer currentMan over herPartner
                if (womanPrefList.indexOf(currentManId) < womanPrefList.indexOf(herManId)) {
//                    remove that match
                    matchingRepo.remove(herManId, desiredWoman);
//                    add new match
                    matchingRepo.put(currentManId, desiredWoman);
//                    remove current man from freeMen Pool
                    freeMen.remove((Integer) currentManId);
//                    set old partner free
                    freeMen.add(herManId);
                }
            }
        }
//        return final repo
        return matchingRepo;
    }

    public static void printSolution(Map<Integer, Integer> repo) {
//        list to format pairs
        List<String> pairs = new ArrayList<>(NUMBER_OF_PAIRS);
        repo.forEach((integer, integer2) -> pairs.add("(" + integer + "," + integer2 + ")"));
        System.out.print("{");
        System.out.print(pairs.toString()
                .replace("[", "")
                .replace("]", ""));
        System.out.println("}");
    }

    public static void main(String[] args) {

//         get desired file name from arguments
        String fileName = Arrays.stream(args)
                .filter(s -> Objects.equals(s, "input1.txt"))
                .findFirst()
                .orElse("not found");

//         receive elements list from method that receives file name
        List<Element> elements = readFileAndReceiveProblemInstance(fileName);

//        split list into men and women
        List<Element> men = elements.subList(0, NUMBER_OF_PAIRS);
        List<Element> women = elements.subList(NUMBER_OF_PAIRS, NUMBER_OF_PAIRS * 2);
//        receive new problem instance
        StableMatchingProblem problemWhenMenPropose =
                StableMatchingProblem.getStableMatchingProblemInstance(men, women);
        StableMatchingProblem problemWhenWomenPropose =
                StableMatchingProblem.getStableMatchingProblemInstance(women, men);

        //print solutions
        System.out.println("Output when men propose:");
        printSolution(solveStableMatchingProblem(problemWhenMenPropose));
        System.out.println("Output when women propose:");
        printSolution(solveStableMatchingProblem(problemWhenWomenPropose));


    }
}

