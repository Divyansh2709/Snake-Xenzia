import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserDataManager {

    private static final String FILE_NAME = "users.txt";

    public static class UserData {
        public int level;
        public int score;
        public int length;

        public UserData(int level, int score, int length) {
            this.level = level;
            this.score = score;
            this.length = length;
        }
    }

    public static Map<String, UserData> loadUserData() {

        Map<String, UserData> data = new HashMap<>();

        File file = new File(FILE_NAME);

        if (!file.exists()) return data;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                if (parts.length >= 4) {

                    String username = parts[0];

                    int level = Integer.parseInt(parts[1]);

                    int score = Integer.parseInt(parts[2]);

                    int length = Integer.parseInt(parts[3]);

                    data.put(username, new UserData(level, score, length));

                }
            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        return data;
    }
}
