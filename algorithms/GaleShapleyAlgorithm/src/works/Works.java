package works;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Works {

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


        private int isMatchedWith = 0;

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

        public int peekHighestPriority() {
            if (!preferenceList.isEmpty())
                try {
                    return preferenceList.get(0);
                } catch (NullPointerException e) {
                    System.out.println("error: null from list");
                }
            return 0;
        }

        public int getHighestPriority() {
            if (!preferenceList.isEmpty())
                try {
                    int highestPriority = preferenceList.get(0);
                    preferenceList.remove(0);
                    return highestPriority;
                } catch (NullPointerException e) {
                    System.out.println("error: null from list");
                }
            return 0;
        }


        public int getMatchedWithId() {
            return isMatchedWith;
        }

        public void matchWith(Element woman) {
            woman.setMatchedWith(this.id);
            isMatchedWith = woman.getId();
        }

        public void setMatchedWith(int id) {
            isMatchedWith = id;
        }

        public void setFree() {
            isMatchedWith = 0;
        }
    }

    private static class StableMatchingProblem {
        private List<Element> men = new ArrayList<>();

        private List<Element> women = new ArrayList<>();

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

        public static StableMatchingProblem getStableMatchingProblemInstance(List<Element> men, List<Element> women) {
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
////        will contain proposal history of each man | mapped manId to List of womanIds
//        Map<Integer, List<Integer>> proposalRepo = new HashMap<>(NUMBER_OF_PAIRS);

////       populate proposalRepo with initial values
//        problem.getMen()
//                .stream()
//                .map(Element::getId)
//                .forEach(id -> proposalRepo.put(id, new ArrayList<>()));

        List<Integer> freeMen = new ArrayList<>(problem.getMen()
                .stream()
                .map(Element::getId)
                .toList());

        List<Integer> freeWomen = new ArrayList<>(problem.getWomen()
                .stream()
                .map(Element::getId)
                .toList());

        List<Integer> currentManProposalHistory = new ArrayList<>();

        while (!freeMen.isEmpty()) {

            int currentManId = freeMen.get(0);
            List<Integer> currentManPrefList = problem.getMenPrefList(currentManId);


            int desiredWoman = currentManPrefList.get(0);
            List<Integer> womanPrefList = problem.getWomenPrefLists(desiredWoman);


            if (freeWomen.contains(desiredWoman)) {
                currentManProposalHistory.add(desiredWoman);
//                proposalRepo.put(currentManId, currentManProposalHistory);

                matchingRepo.put(currentManId, desiredWoman);

                freeMen.remove(freeMen.indexOf(currentManId));
                freeWomen.remove(freeWomen.indexOf(desiredWoman));
            } else {
                int herManId = 0;
                herManId = matchingRepo.keySet()
                        .stream()
                        .filter(integer -> matchingRepo.get(integer)
                                .equals(desiredWoman))
                        .findFirst()
                        .orElse(-1);

                if (womanPrefList.indexOf(currentManId) < womanPrefList.indexOf(herManId)) {

                    currentManProposalHistory.add(desiredWoman);
//                    proposalRepo.put(currentManId, currentManProposalHistory);

                    matchingRepo.remove(herManId, desiredWoman);
                    matchingRepo.put(currentManId, desiredWoman);

                    freeMen.remove(freeMen.indexOf(currentManId));
                    freeMen.add(herManId);

                } else {
                    currentManPrefList.remove(0);
                    currentManProposalHistory.add(desiredWoman);
                }


            }

        }
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
                .filter(s -> Objects.equals(s, "input3.txt"))
                .findFirst()
                .orElse("not found");

//         receive elements list from method that receives file name
        List<Element> elements = readFileAndReceiveProblemInstance(fileName);

//        split list into men and women
        List<Element> men = elements.subList(0, NUMBER_OF_PAIRS);
        List<Element> women = elements.subList(NUMBER_OF_PAIRS, NUMBER_OF_PAIRS * 2);
//        receive new problem instance
        StableMatchingProblem problemWhenMenPropose = StableMatchingProblem.getStableMatchingProblemInstance(men, women);
        StableMatchingProblem problemWhenWomenPropose = StableMatchingProblem.getStableMatchingProblemInstance(women, men);

        //print solutions
        System.out.println("Output when men propose:");
        printSolution(solveStableMatchingProblem(problemWhenMenPropose));
        System.out.println("Output when women propose:");
        printSolution(solveStableMatchingProblem(problemWhenWomenPropose));


    }
}

