package racingcar;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.HashMap;
import java.util.Map;
import camp.nextstep.edu.missionutils.Console;

public class Application {
    // Map<String , Integer> carNameMap = new HashMap<>();
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
        return Integer.parseInt(Console.readLine());
    }

    private static Map<String , Integer> registrationCar(String inputCarName){
        String[] carNameArray = inputCarName.split(",");
        Map<String , Integer> tempMap = new HashMap<>();
        for (String carName : carNameArray) {
           tempMap.put(validate(carName),0);
        }
        return tempMap;
    }

    private static String validate(String carName){
        if(carName.length() > 5){
            throw new IllegalArgumentException("자동차 이름은 5자 이하만 가능합니다: " + carName);
        }else{
            return carName;
        }
    }

    private static void raceStart(Map<String , Integer> carList , Integer inputMaxMove){
        for (int i = 0; i < inputMaxMove; i++) {
            System.out.println("\n");
            moveFoward(carList);
        }
    }

    private static void moveFoward(Map<String , Integer> carList){
        for (String car : carList.keySet()) {
            carList.put(car, randomGo(carList.get(car)));
            System.out.println(car + " : " + forwardStatPrint(carList.get(car)));
        }
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
}

