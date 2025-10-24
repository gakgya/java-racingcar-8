package racingcar;

import camp.nextstep.edu.missionutils.Randoms;
import camp.nextstep.edu.missionutils.Console;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Application {
    private static final Pattern POSITIVE_NUMBER_PATTERN = Pattern.compile("^[1-9]\\d*$");

    public static void main(String[] args) {
        String inputCarList = inputCar();
        Integer inputMaxMove = inputMaxMove();

        Map<String, Integer> carMap = registerCars(inputCarList);
        raceStart(carMap, inputMaxMove);
    }

    private static String inputCar() {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        return Console.readLine();
    }

    private static Integer inputMaxMove() {
        System.out.println("시도할 횟수는 몇 회인가요?");
        return inputPositiveNumber(Console.readLine());
    }

    private static Map<String, Integer> registerCars(String inputCarNames) {
        String[] carNames = inputCarNames.split(",");
        Map<String, Integer> carMap = new HashMap<>();

        for (String carName : carNames) {
            validateDuplicate(carMap, carName);
            carMap.put(validateCarName(carName), 0);
        }
        return carMap;
    }

    private static void validateDuplicate(Map<String, Integer> carMap, String carName) {
        if (carMap.containsKey(carName)) {
            throw new IllegalArgumentException("중복차량이 존재합니다! : " + carName);
        }
    }

    private static String validateCarName(String carName) {
        if (carName.length() > 5) {
            throw new IllegalArgumentException("자동차 이름은 5자 이하만 가능합니다 : " + carName);
        }
        return carName;
    }

    private static void raceStart(Map<String, Integer> carMap, Integer maxMove) {
        for (int i = 0; i < maxMove; i++) {
            System.out.println();
            carMap = moveForward(carMap);
            printRaceProgress(carMap);
        }
        printWinner(carMap);
    }

    private static Map<String, Integer> moveForward(Map<String, Integer> carMap) {
        for (String car : carMap.keySet()) {
            carMap.put(car, randomMove(carMap.get(car)));
        }
        return carMap;
    }

    private static int randomMove(int moveCount) {
        if (Randoms.pickNumberInRange(0, 9) >= 4) {
            return moveCount + 1;
        }
        return moveCount;
    }

    private static void printRaceProgress(Map<String, Integer> carMap) {
        for (Map.Entry<String, Integer> entry : carMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + forwardStat(entry.getValue()));
        }
    }

    private static String forwardStat(int moveCount) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < moveCount; i++) {
            builder.append("-");
        }
        return builder.toString();
    }

    private static void printWinner(Map<String, Integer> carMap) {
        System.out.println("최종 우승자 : " + String.join(", ", getWinners(carMap)));
    }

    private static List<String> getWinners(Map<String, Integer> carMap) {
        if (carMap.isEmpty()) {
            return Collections.emptyList();
        }
        int maxMove = findMaxValue(carMap);
        return findKeysByValue(carMap, maxMove);
    }

    private static int findMaxValue(Map<String, Integer> carMap) {
        return Collections.max(carMap.values());
    }

    private static List<String> findKeysByValue(Map<String, Integer> carMap, int value) {
        List<String> keys = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : carMap.entrySet()) {
            if (entry.getValue().equals(value)) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    private static int inputPositiveNumber(String input) {
        if (!isPositiveNumber(input)) {
            throw new IllegalArgumentException("최대 횟수는 양수만 가능합니다!");
        }
        return Integer.parseInt(input);
    }

    private static boolean isPositiveNumber(String input) {
        return POSITIVE_NUMBER_PATTERN.matcher(input).matches();
    }
}
