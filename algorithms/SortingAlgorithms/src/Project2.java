import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Project2 {

    public static class Problem {
//        desired value for i
        private final int iValue;
//        size of the data
        private final int size;
//        the data
        private final List<Integer> list;

        private Problem(int val, int size, List<Integer> list) {
            this.iValue = val;
            this.size = size;
            this.list = list;
        }

        /*
         * Finds pivot
         *   and
         * places smaller elements to the left of it
         *   and
         * places larger elements to the right of it*/
        public int findPivot(List<Integer> integers, int start, int end) {

            int pivot = integers.get(end);

            int index = (start - 1);
//            iterate over
            for (int iterator = start; iterator < end; iterator++) {
//               if current iteration value is <= pivot
                if (integers.get(iterator) <= pivot) {
                    index++;
//                    swap
                    swap(integers, index, iterator);
                }
            }
//            swap
            swap(integers, index + 1, end);
//            return
            return index + 1;
        }
        /*
        * simply performs swap of values within the list
        * */
        private void swap(List<Integer> list, int i, int k) {
            int temp = list.get(i);
            list.set(i, list.get(k));
            list.set(k, temp);
        }
        /*
         * QuickSort
         * */
        public void quickSort(List<Integer> integers, int start, int end) {
            if (start < end) {
//                find pivot
                int pivot = findPivot(integers, start, end);

//                recursively sorts integers before and after found pivot
                quickSort(integers, start, pivot - 1);
                quickSort(integers, pivot + 1, end);
            }
        }
        /*
         * returns value for printing
         * */
        private int quickSortResult() {
            List<Integer> copy = list;
            quickSort(copy, 0, size - 1);
            return copy.get(iValue);
        }

        /*
        * finds median from the chunk of data
        * */
        public int findPivotOfTheChunk(int[] integers, int start, int end) {
//            size of the chunk for finding median
            int sizeOfTheChunk = end - start + 1;
            int medianIndex = 0;

            int[] mediansArray = new int[sizeOfTheChunk];
            int iterator = start;
//            in this iteration iterator is the beginning of the chunk
            while (iterator <= end) {
//                collect (potential) medians from chunk values
                mediansArray[medianIndex] = integers[iterator];
//                iterate the medianIndex
                medianIndex += 1;
//                next iteration
                iterator += 1;
            }
//           sort
            Arrays.sort(mediansArray);
//            return median
            return mediansArray[sizeOfTheChunk / 2];
        }


        /*
        * Median of Medians
        * */
        public int medianOfMedians(int[] integers, int ithValue, int size) {
            int theMedian, medianIndex;
//            we are going to keep track of 3 arrays
//            mediansArray:        for keeping found medians from chunks
            int[] mediansArray = new int[size / 5];
//            leftOfMedianArray:   for keeping values less than theMedian
            int[]leftOfMedianArray = new int[size];
//            rightOfMedianArray:  for keeping values greater than the Median
            int[]rightOfMedianArray =  new int[size];
//            final value for ith integer to be returned
            int desiredValue;
//            to find the desired value
            int left = 0, right = 0;
//            checking if the data is small (less than 5)
            if (size < 5) {
//                clone initial data (since the data itself will be modified)
                int[] clone = Arrays.copyOf(integers, size);
//                sort
                Arrays.sort(clone);
//                desiredValue found
                desiredValue = clone[ithValue];
            }
//            meaning size is larger
            else {
//                iterate and find medians
                for (medianIndex = 0; medianIndex < size / 5; medianIndex++) {
//                    find medians and populate the mediansArray
                    mediansArray[medianIndex] =
                            findPivotOfTheChunk(integers, 5 * medianIndex, 5 * medianIndex + 4);
                }
//                find median of medians from the mediansArray
                theMedian = medianOfMedians(mediansArray, size / 10, size / 5);

//                put values to the left and right of the median
                for (medianIndex = 0; medianIndex < size; medianIndex++) {
//                   if current value is less than theMedian
                    if (integers[medianIndex] <= theMedian) {
//                        swap
                        leftOfMedianArray[left] = integers[medianIndex];
                        left += 1;
                    }
//                    meaning is greater than
                    else {
//                        swap
                        rightOfMedianArray[right] = integers[medianIndex];
                        right += 1;
                    }
                }
//                desired value is to the left
                if (ithValue < left) {
//                    desired value found
                    desiredValue = medianOfMedians(leftOfMedianArray, ithValue, left);
                }
//                desired value is to the right
                else {
//                    desired value found
                    desiredValue = medianOfMedians(rightOfMedianArray, ithValue - left, right);
                }
            }
            return desiredValue;
        }


        /*
         * returns value for printing
         * */
        private int medianOfMediansResult() {
//            clone of original list
            List<Integer> copy = list;
//            java ArrayLists won't work for this algorithm
//            we need fixed-size data structures
//            since we are going to heavily depend on sizes
            int[] ints = copy.stream()
                    .mapToInt(Integer::valueOf)
                    .toArray();
            return medianOfMedians(ints, iValue, copy.size() - 1);
        }

        public void solveWithBothAlgorithms() {
            System.out.println("Quicksort finds " + quickSortResult() + " .");
            System.out.println("MedOfMed finds " + medianOfMediansResult() + " .");
        }

        /*
         * Returns problem instance
         * */
        public static Problem getProblem(int val, int size, List<Integer> list) {
            return new Problem(val, size, list);
        }

        @Override
        public String toString() {
            return "i value =\t" + iValue +
                    "\nsize =\t" + size +
                    "\nlist =\t" + list;
        }
    }

    /*
     * Reads the input file and gives sorting problem instance
     * */
    public static Problem readFileAndReceiveProblemInstance(String fileName) {
//        will hold integer values
        List<Integer> list = new ArrayList<>();
//        will hold desired value for i and the size
        int iValue = 0, size = 0;
//        current line to work with
        int lineCount = 1;
//        keeps line content
        String currentData;
        try {
            Scanner reader = new Scanner(new File(fileName));
            while (reader.hasNextLine()) {
                try {
//                    read line
                    currentData = reader.nextLine();
//                    read integer values to queue
                    Queue<Integer> firstLine = Arrays.stream(currentData.split(" "))
                            .filter(s -> !Objects.equals(s, ""))
                            .map(Integer::valueOf)
                            .collect(Collectors.toCollection(LinkedList::new));
//                    first line contains size and desired value
                    if (lineCount == 1) {
//                        get desired value
                        iValue = Optional.ofNullable(firstLine.poll())
                                .orElse(-500);
//                        get size
                        size = Optional.ofNullable(firstLine.poll())
                                .orElse(-500);
//                        clear queue
                        firstLine.clear();
                    }
//                    other lines that contain integer values
                    else {
//                        read values to list
                        firstLine.stream()
                                .map(integer -> Optional.ofNullable(integer)
                                        .orElse(-500))
                                .forEach(list::add);
//                        clear queue
                        firstLine.clear();
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
        // return sorting problem instance
        return Problem.getProblem(iValue, size, list);
    }


    public static void main(String[] args) {
        //         get desired file name from arguments
        String fileName = Arrays.stream(args)
                .filter(s -> Objects.equals(s, "input2.txt"))
                .findFirst()
                .orElse("not found");
//        receive problem instance
        Problem problem = readFileAndReceiveProblemInstance(fileName);
//        solve the problem
        problem.solveWithBothAlgorithms();
    }
}
