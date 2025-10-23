package racingcar;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import camp.nextstep.edu.missionutils.Console;

public class Application {
    // Map<String , Integer> carNameMap = new HashMap<>();
    private static final Pattern POSITIVE_NUMBER_PATTERN = Pattern.compile("^[1-9]\\d*$");
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        String inputCarList = inputCar();
        Integer inputMaxMove = inputMaxMove();
        Map<String , Integer> carNameMap = registrationCar(inputCarList);
        raceStart(carNameMap,inputMaxMove);
    }

    private static String inputCar(){
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        return Console.readLine();
    }

    private static Integer inputMaxMove(){
        System.out.println("시도할 횟수는 몇 회인가요?");
        Integer tempMaxMove = inputPositiveNumber(Console.readLine());
        return tempMaxMove;
    }

    private static Map<String , Integer> registrationCar(String inputCarName){
        String[] carNameArray = inputCarName.split(",");
        Map<String , Integer> tempMap = new HashMap<>();
        for (String carName : carNameArray) {
            if(!tempMap.containsKey(carName)){
                tempMap.put(validate(carName),0);
            }else{
                throw new IllegalArgumentException("중복차량이 존재합니다! : " + carName);
            }
        }
        return tempMap;
    }

    private static String validate(String carName){
        if(carName.length() > 5){
            throw new IllegalArgumentException("자동차 이름은 5자 이하만 가능합니다 : " + carName);
        }else{
            return carName;
        }
    }

    private static void raceStart(Map<String , Integer> carList , Integer inputMaxMove){
        for (int i = 0; i < inputMaxMove; i++) {
            System.out.println("\n");
            carList = moveFoward(carList);
        }
        printWinner(carList);
    }

    private static Map<String , Integer> moveFoward(Map<String , Integer> carList){
        for (String car : carList.keySet()) {
            carList.put(car, randomGo(carList.get(car)));
            System.out.println(car + " : " + forwardStatPrint(carList.get(car)));
        }
        return carList;
    }

    private static Integer randomGo(Integer moveStat){
        if(Randoms.pickNumberInRange(0, 9)>=4){
            return moveStat+1;
        }else{
            return moveStat;
        }
    }

    private static String forwardStatPrint(Integer moveStat){
        String temp = "";
        for(int i = 0; i < moveStat; i++){
            temp += "-";
        }
        return temp;
    }

    private static void printWinner(Map<String , Integer> carList){ 
        System.out.println("최종 우승자 : " + String.join(", ",getMaxValueKeys(carList)));
    }

    private static List<String> getMaxValueKeys(Map<String, Integer> carList) {
        if (carList.isEmpty()) {
            return Collections.emptyList();
        }

        int maxValue = findMaxValue(carList);
        return findKeysByValue(carList, maxValue);
    }

    private static int findMaxValue(Map<String, Integer> carList) {
        return Collections.max(carList.values());
    }

    private static List<String> findKeysByValue(Map<String, Integer> carList, int value) {
        List<String> keys = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : carList.entrySet()) {
            if (entry.getValue().equals(value)) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    private static int inputPositiveNumber(String input) {

        if (isPositiveNumber(input)) {
            return Integer.parseInt(input);
        }else{
            throw new IllegalArgumentException("최대 횟수는 양수만 가능합니다!");
        }
        
    }

    private static boolean isPositiveNumber(String input) {
        return POSITIVE_NUMBER_PATTERN.matcher(input).matches();
    }

}

