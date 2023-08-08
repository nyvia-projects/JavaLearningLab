import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class algorithm {
    public static Map<Integer, Integer> galeShapleyAlgo(int n, Map<Integer, List<Integer>> menpref,
                                                        Map<Integer, List<Integer>> womanpref, List<Integer> menlist, List<Integer> womanlist) {

        // Initialization
        List<Integer> menUnmatched = new ArrayList<>();
        List<Integer> womanUnmatched = new ArrayList<>();
        Map<Integer, Integer> pair = new HashMap<Integer, Integer>();
        Map<Integer, Integer> menIndex = new HashMap<Integer, Integer>();

        for (int i = 0; i < n; i++) {
            menIndex.put(menlist.get(i), 0);
        }
        for (int i = 0; i < n; i++) {
            menUnmatched.add(menlist.get(i));
            womanUnmatched.add(womanlist.get(i));
        }

        // algorithm starts
        while (!menUnmatched.isEmpty()) {

            int man = menUnmatched.get(0);
            List<Integer> hisPref = menpref.get(man);
            int indexnum = menIndex.get(man);
            int woman = hisPref.get(indexnum);
            List<Integer> herPref = womanpref.get(woman);

            if (womanUnmatched.contains(woman)) {
                pair.put(man, woman);
                menUnmatched.remove(menUnmatched.indexOf(man));
                womanUnmatched.remove(womanUnmatched.indexOf(woman));
            } else if (pair.containsValue(woman) && (hisPref.contains(woman))) {
                // getting current partner
                int currentPartner = 0;
                for (Object partner : pair.keySet()) {
                    if (pair.get(partner).equals(woman)) {
                        currentPartner = (int) partner;
                    }
                }
                if (herPref.indexOf(man) < herPref.indexOf(currentPartner)) {

                    pair.remove(currentPartner, woman);
                    menUnmatched.add(currentPartner);
                    pair.put(man, woman);
                    menUnmatched.remove(menUnmatched.indexOf(man));
                }
            }

            indexnum++;
            menIndex.put(man, indexnum);

        }

        return pair;

    }
}
   /* public static void solveStableMatchingProblem(StableMatchingProblem problem) {
//        will contain proposal history | mapped man_id -> list of boolean values
        Map<Integer, List<Integer>> proposalRepo = new HashMap<>(NUMBER_OF_PAIRS);

//        will contain current and final matches
        Map<Integer, Integer> matchingRepo = new HashMap<>(NUMBER_OF_PAIRS);

//        define the state of 'has not proposed'
        List<Integer> hasNotProposed = List.of();

        final List<Element> allMen = new ArrayList<>(problem.getMen()
                .stream()
                .toList());

        allMen.stream()
                .map(Element::getId)
                .forEach(integer -> proposalRepo.put(integer, new ArrayList<>()));

//        stacks to iterate through
        Stack<Element> freeMen = new Stack<>();
        List<Integer> freeWomen = new ArrayList<>(problem.getWomen()
                .stream()
                .map(Element::getId)
                .toList());

        Collections.reverse(problem.getMen());

        problem.getMen()
                .forEach(freeMen::push);


//        System.out.println(proposalRepo);
//        while there are NO free man && not a single man who hasn't proposed
        while (!freeMen.isEmpty()) {//&& proposalRepo.containsValue(hasNotProposed)) {
//            get top man to operate with
            Element currentMan = freeMen.pop();
            int currentManId = currentMan.getId();

            System.out.println("Current man id: " + currentManId);

            Queue<Integer> currentManPreferenceList = new PriorityQueue<>();

            currentManPreferenceList.addAll(currentMan.getPreferenceList());

            System.out.println("his pref list: " + currentManPreferenceList);

            int highestPriorityWoman = currentManPreferenceList.poll();

        System.out.println("Highest Woman: " + highestPriorityWoman);

        List<Integer> womanPreferenceList = new ArrayList<>(problem.getWomen()
                .stream()
                .filter(element -> element.getId() == highestPriorityWoman)
                .map(Element::getPreferenceList)
                .findFirst()
                .orElseGet(List::of));


        List<Integer> currentManProposalHistory = new ArrayList<>(
                List.copyOf(proposalRepo.get(currentManId)));

//            if a free woman who is in the highest priority of the current man
//            and
//            current man hasn't proposed to that woman
            if (freeWomen.contains(highestPriorityWoman) && !currentManProposalHistory.contains(highestPriorityWoman)) {
//                update this proposal
                currentManProposalHistory.add(highestPriorityWoman);
                System.out.println("history for man" + currentManId + ":" + currentManProposalHistory);
//                record proposal in proposalRepo
                proposalRepo.put(currentManId, currentManProposalHistory);
//                record match in matchingRepo | also removes value from Queue
                matchingRepo.put(currentManId, highestPriorityWoman);

                System.out.println(matchingRepo);
                freeMen.pop();
                freeWomen.remove(highestPriorityWoman-1);
            }
//            meaning if woman is not free
            else {//{if (matchingRepo.containsValue(highestPriorityWoman)){
                int herManId = matchingRepo.keySet()
                        .stream()
                        .filter(integer -> matchingRepo.get(integer)
                                .equals(highestPriorityWoman))
                        .findFirst()
                        .orElse(-1);
//                if woman doesn't prefer currentMan over hers
                if (womanPreferenceList.indexOf(currentManId - 1) > womanPreferenceList.indexOf(herManId - 1)) {
                    currentManProposalHistory.add(highestPriorityWoman);
                    proposalRepo.put(currentManId, currentManProposalHistory);
                    System.out.println("matching repo: "+matchingRepo);

                }
//                woman does prefer currentMan over hers
                else {
                    currentManProposalHistory.add(highestPriorityWoman);
                    proposalRepo.put(currentManId, currentManProposalHistory);
                    matchingRepo.remove(herManId, highestPriorityWoman);
                    matchingRepo.put(currentManId, highestPriorityWoman);

                    freeMen.push(allMen.get(herManId - 1));
                    System.out.println(matchingRepo);
                }
            }
        }
        matchingRepo.forEach((integer, integer2) -> System.out.println(integer + " " + integer2));

    }*/

/*
//        while there are NO free man && not a single man who hasn't proposed
        while (problem.getFreeMenIds()
                .size() != 0 && proposalRepo.containsValue(hasNotProposed)) {
//            get top man to operate with
            Element currentMan = stack.pop();
            int hisHighestPriority = currentMan.getHighestPriority();
//            if a free woman who is in the highest priority of the current man
//            and
//            current man hasn't proposed to that woman
            if (women
                    .stream()
                    .filter(element -> element.isMatchedWith == 0)
                    .anyMatch(element -> element.getId() == hisHighestPriority)
                    && !proposalRepo.get(currentMan.getId())
                    .get(hisHighestPriority)) {
//                match them (sets both objects isMatchedWith attributes to each other's ids)
                currentMan.matchWith(women.get(hisHighestPriority));
//                record proposal in proposalRepo
                matchingRepo.put(currentMan.getId(), currentMan.preferenceList.peek());
//                record match in matchingRepo
                matchingRepo.put(currentMan.getId(), currentMan.preferenceList.poll());
            }
//            meaning if woman is not free
            else {

//                    handled in implementation
                assert currentMan.preferenceList.peek() != null;
//                if woman doesn't prefer currentMan over hers
                if () {

                }
//                if she does prefer currentMan over hers
                else {
                }

            }*/