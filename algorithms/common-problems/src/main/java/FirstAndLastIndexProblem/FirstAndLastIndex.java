package FirstAndLastIndexProblem;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class FirstAndLastIndex {

    private final int target;

    private final ArrayList<Integer> ary;

    private final int[] defaultIndices = {-1, -1};



    public int[] getFirstAndLastIndexWithLinearSearch() {

        Integer first = ary.stream()
                .filter(integer -> integer == target)
                .map(ary::indexOf)
                .findFirst()
                .orElse(null);

        if (first == null)
            return defaultIndices;

        long size = ary.stream()
                .filter(integer -> integer == target)
                .count();

        int last = first + (int) size - 1;

        return new int[]{first, last};

    }

    public int[] getFirstAndLastIndexWithBinarySearch() {

        int lastElement = ary.get(ary.size() - 1);

        if (ary.size() == 0
                || ary.get(0) > target
                || lastElement < target)
            return defaultIndices;
        int first = getFirst(target, ary);
        int last = getLast(target, ary);

        return new int[]{first, last};
    }

    private int getFirst(int target, ArrayList<Integer> ary) {
        if (ary.get(0) == target)
            return 0;

        int mid;
        int left = 0;
        int right = ary.size() - 1;

        while (left <= right) {
            mid = (left + right) / 2;
            if (ary.get(mid) == target && ary.get(mid - 1) < target)
                return mid;
            else if (ary.get(mid) < target)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return -1;
    }

    private int getLast(int target, ArrayList<Integer> ary) {
        if (ary.get(ary.size() - 1) == target)
            return ary.size() - 1;

        int mid;
        int left = 0;
        int right = ary.size() - 1;

        while (left <= right) {
            mid = (left + right) / 2;
            if (ary.get(mid) == target && ary.get(mid + 1) > target)
                return mid;
            else if (ary.get(mid) > target)
                right = mid - 1;
            else
                left = mid + 1;
        }
        return -1;
    }


}

