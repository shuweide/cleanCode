package idv.code.leetcode;

import org.junit.Assert;

import java.util.Stack;

public class MaxStack {
    private Stack<Integer> stack = new Stack<>();
    private Stack<int[]> maxStack = new Stack<>();

    public MaxStack() { }

    public void push(int x) {
        stack.push(x);

        if(maxStack.isEmpty() || x > maxStack.peek()[0]){
            maxStack.push(new int[]{x,1});
        }else if(x == maxStack.peek()[0]){
            maxStack.peek()[1]++;
        }
    }

    public void pop() {
        if (stack.peek().equals(maxStack.peek()[0])) {
            maxStack.peek()[1]--;
        }

        if (maxStack.peek()[1] == 0) {
            maxStack.pop();
        }

        stack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMax() {
        return maxStack.peek()[0];
    }

    public static void main(String[] args) {
        MaxStack maxStack = new MaxStack();
        maxStack.push(-2);
        maxStack.push(0);
        maxStack.push(10);
        maxStack.push(-3);
        maxStack.push(10);
        maxStack.push(0);
        Assert.assertEquals(10, maxStack.getMax());
        maxStack.pop();
        Assert.assertEquals(10, maxStack.top());
        Assert.assertEquals(10, maxStack.getMax());
        maxStack.pop();
        maxStack.pop();
        maxStack.pop();
        Assert.assertEquals(0, maxStack.getMax());
    }
}
