package com.dingxy.common;

import java.util.ArrayList;
import java.util.List;


public class Calculator {

	public static void main(String[] args) {
		
		double[] day = {30,28,30,30,31,30,31,31,30,31,30,31};
		
		Double money = new Double(1000);
		
		Double lixi = 6.0;
		
		List<Double> moneyList = new ArrayList<Double>();
		for(int i = 0; i < 12; i++){
			if(i == 0){
				Double lastMoney = money * (1 + lixi / 100 / 365 * day[i]);
				moneyList.add(lastMoney);
			}else{
				Double lastMoney = moneyList.get(i - 1) *(1 + lixi / 100 / 365 * day[i % 12]);
				moneyList.add(lastMoney);
			}
		}
		Double allmoney = 0.0;
		for(int i = 0; i < moneyList.size(); i++){
			System.out.println(moneyList.get(i));
			allmoney += moneyList.get(i);
		}
		System.out.println(allmoney);
	}
}
