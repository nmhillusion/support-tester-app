package app.netlify.nmhillusion.support_tester_app.runner;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * date: 2022-05-06
 * <p>
 * created-by: nmhillusion
 */

public class RemoveDuplicate {

    @Test
    public void testRemoveDuplicate() {
//        assertEquals("abc", "abhtuc");
        assertEquals("aa", removeDuplicates("deeedbbcccbdaa", 3));
        assertEquals("ps", removeDuplicates("pbbcggttciiippooaais", 2));
        assertEquals("abcd", removeDuplicates("abcd", 2));
        assertEquals("ybth", removeDuplicates("yfttttfbbbbnnnnffbgffffgbbbbgssssgthyyyy", 4));
        assertEquals("vqm", removeDuplicates("nnwssswwnvbnnnbbqhhbbbhmmmlllm", 3));

//        System.out.println(result1);
//        System.out.println(result2);
//        System.out.println(result3);
    }

    public String removeDuplicates(String s, int k) {
        final List<Character> cList = Arrays.stream(s.split("")).map(str -> str.charAt(0)).collect(Collectors.toList());
        Stack<DuplicateItem> stack = new Stack<>();

        for (int idx = 0; idx < cList.size(); ++idx) {
            DuplicateItem item = null;
            if (stack.isEmpty()) {
                item = new DuplicateItem()
                        .setC(cList.get(idx))
                        .setStartIdx(idx)
                        .setEndIdx(idx)
                ;

                stack.add(item);
            } else {
                final DuplicateItem peekItem = stack.peek();

                if (peekItem.getC() == cList.get(idx)) {
                    peekItem.setEndIdx(idx);

                    if (peekItem.isAbleToRemove(k)) {
                        final DuplicateItem popItem = stack.pop();
                        deleteDuplicates(cList, popItem.getStartIdx(), popItem.getEndIdx());

                        if (!stack.isEmpty()) {
                            idx = stack.peek().endIdx;
                        } else {
                            idx = 0;
                        }
                    }
                } else {
                    stack.add(new DuplicateItem()
                            .setC(cList.get(idx))
                            .setStartIdx(idx)
                            .setEndIdx(idx)
                    );
                }
            }
        }

        final StringBuilder stringBuilder = new StringBuilder();
        cList.forEach(stringBuilder::append);
        return stringBuilder.toString();
    }

    private void deleteDuplicates(List<Character> cList, int startIndex, int endIndex) {
        try {
            if (endIndex >= startIndex && startIndex >= 0) {
                cList.subList(startIndex, endIndex + 1).clear();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

class DuplicateItem {
    char c;
    int startIdx;
    int endIdx;

    public char getC() {
        return c;
    }

    public DuplicateItem setC(char c) {
        this.c = c;
        return this;
    }

    public int getStartIdx() {
        return startIdx;
    }

    public DuplicateItem setStartIdx(int startIdx) {
        this.startIdx = startIdx;
        return this;
    }

    public int getEndIdx() {
        return endIdx;
    }

    public DuplicateItem setEndIdx(int endIdx) {
        this.endIdx = endIdx;
        return this;
    }

    public boolean isAbleToRemove(int k) {
        return endIdx - startIdx == k - 1 && startIdx >= 0;
    }

    @Override
    public String toString() {
        return "DuplicateItem{" +
                "c='" + c + '\'' +
                ", startIdx=" + startIdx +
                ", endIdx=" + endIdx +
                '}';
    }
}