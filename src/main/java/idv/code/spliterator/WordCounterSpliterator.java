package idv.code.spliterator;

import java.util.Spliterator;
import java.util.function.Consumer;

public class WordCounterSpliterator implements Spliterator<Character> {
    private final String string;
    private int currentChar = 0;

    public WordCounterSpliterator(String string) {
        this.string = string;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        action.accept(string.charAt(currentChar++)); //處理當前Char
        return currentChar < string.length(); //還有Char要處理，return true
    }

    //定義了拆分要遍歷的數據結構的邏輯
    @Override
    public Spliterator<Character> trySplit() {
        int currentSize = string.length() - currentChar;
        if (currentSize < 10) {
            return null; //返回null表示要解析的String已經足夠小，可以順序處理
        }
        for (int splitPos = currentSize / 2 + currentChar; splitPos < string.length(); splitPos++) {
            if (Character.isWhitespace(string.charAt(splitPos))) {
                //創建一個新的WordCounterSpliterator來解析string從開始要拆分的部分
                String subString = string.substring(currentChar, splitPos);
                Spliterator<Character> spliterator = new WordCounterSpliterator(subString);
                System.out.println("this time split string :" + subString);
                currentChar = splitPos;
                return spliterator;
            }
        }
        return null;
    }

    @Override
    public long estimateSize() {
        return string.length() - currentChar;
    }

    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }
}
