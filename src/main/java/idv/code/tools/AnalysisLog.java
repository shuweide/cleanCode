package idv.code.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AnalysisLog {

    //grep ] - get location: 後的檔案
    private static final String FILENAME = "D:\\workspace\\clean-code\\src\\main\\resources\\test.log";

    static class User {
        int leavingCount = 0;
        int loginCount = 0;
        List<String> messages = new ArrayList<>();

        void processAction(String action) {
            if (action.equals("FortunaLogin")) {
                loginCount++;
            }
            if (action.equals("FortunaLeaving")) {
                leavingCount++;
            }
        }

        @Override
        public String toString() {

            System.out.println("=============================");
            System.out.printf("login time:%d, logout time:%d \n", loginCount, leavingCount);
            messages.forEach(System.out::println);
            System.out.println("=============================");
            return "";
        }
    }

    public static void main(String[] args) {

        Map<String, User> userMap = new HashMap<>();
        Set<String> deletes = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {

                String[] strings = sCurrentLine.split("\\[|\\]|\\:| ");
                String name = strings[19];
                String action = strings[17];
                long time = Long.valueOf(strings[2].concat(strings[3]));

                if (time > 1050L && time < 2200L) {
                    User user = null;
                    if (userMap.containsKey(name)) {
                        user = userMap.get(name);
                    } else {
                        user = new User();
                        userMap.put(name, user);
                    }

                    user.processAction(action);
                    user.messages.add(sCurrentLine);
                }else{
                    deletes.add(name);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        deletes.forEach(userMap::remove);

        for (Map.Entry<String, User> e : userMap.entrySet()) {
            if (e.getValue().leavingCount < e.getValue().loginCount) {
                System.out.printf("name:%s \ndetail:\n %s", e.getKey(), e.getValue());
            }
        }

    }
}
