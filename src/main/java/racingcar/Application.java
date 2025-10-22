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
        Map<String , Integer> carNameMap = validate(inputCarList);
    }

    private static String inputCar(){
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        return Console.readLine();
    }

    private static Integer inputMaxMove(){
        System.out.println("시도할 횟수는 몇 회인가요?");
        return Integer.parseInt(Console.readLine());
    }

    private static Map<String , Integer> validate(String inputCarName){
        String[] carNameArray = inputCarName.split(",");
        Map<String , Integer> tempMap = new HashMap<>();
        for (String carName : carNameArray) {
            if(carName.length() > 5){
                 throw new IllegalArgumentException("자동차 이름은 5자 이하만 가능합니다: " + carName);
            }else{
                tempMap.put(carName, 0);
            }
        }
        return tempMap;
    }
}

