package serchComparsionString;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;



public class serchMain {

	static ArrayList<PostInf> postList;
	static ArrayList<String> serchData;
	
	static private int serchCnt;

	public static void main(String[] args) {
		postList = new ArrayList<PostInf>();
		serchData = new ArrayList<String>();
		// パラメタチェック
		if (args.length != 1) {
			System.out.println("ファイルパスを指定してください");
			return;
		}
		String path = args[0];

		// csvファイル読み込み
		readCsv(path);
		
		serchCnt = serchData.size();
		serchCnt = 10000;
		// 線形検索
		LinearSearch();

		// 改良型線形検索
		AmeliorationLinearSearch();

		//二分探索
		ArgoSearchBinary();
		

		
		// hashmap検索
		HashMapSerch();
		
		// TreeMap検索
		TreeMapSerch();
		// LinkedHashMap検索
		LinkedHashMapSerch();

	}

	static void readCsv(String path) {
		try {

			File f = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(f));

			String line;
			// 1行ずつCSVファイルを読み込む
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",", 0); // 行をカンマ区切りで配列に変換
				int number = Integer.parseInt(data[0]);

				StringBuffer buf = new StringBuffer();
				for (int i = 1; i < data.length; i++) {
					buf.append(data[i]);
				}
				String s = buf.toString();
				PostInf postData = new PostInf(number, s);
				postList.add(postData);
				serchData.add(s);
			}
			br.close();

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	static void LinearSearch() {

		// 時刻取得
		long startTime = System.currentTimeMillis();

		Random rnd = new Random();

		for (int i = 0; i < serchCnt; i++) {

			int r = rnd.nextInt(serchData.size());

			String sData = serchData.get(r);

			for (int iLoop = 0; iLoop < postList.size(); iLoop++) {
				String postaddr = postList.get(iLoop).getAddr();
				if (sData.equals(postaddr)) {
					//System.out.println(postList.get(iLoop).getAddr());
					break;
				}
			}
		}
		// 時刻測定
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("List:" + totalTime);
	}

	static void AmeliorationLinearSearch() {

		// 時刻取得
		long startTime = System.currentTimeMillis();

		Random rnd = new Random();

		int startPos = 0;
		for (int i = 0; i < serchCnt; i++) {

			int r = rnd.nextInt(serchData.size());

			String sData = serchData.get(r);
			boolean matchFlg = false;
			for (int iLoop = startPos; iLoop < postList.size(); iLoop++) {
				String postaddr = postList.get(iLoop).getAddr();
				if (sData.equals(postaddr)) {
					startPos = iLoop;
					matchFlg = true;
					//System.out.println(postList.get(iLoop).getAddr());
					break;
				}
			}
			if (!matchFlg) {
				for (int iLoop = 0; iLoop < startPos; iLoop++) {
					String postaddr = postList.get(iLoop).getAddr();
					if (sData.equals(postaddr)) {
						startPos = iLoop;
						matchFlg = true;
						//System.out.println(postList.get(iLoop).getAddr());
						break;
					}
				}
			}
		}
		// 時刻測定
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("AmeliorationLine:" + totalTime);
	}
	
	static void ArgoSearchBinary() {

		// 時刻取得
		long startTime = System.currentTimeMillis();

		Random rnd = new Random();

		for (int i = 0; i < serchCnt; i++) {

			int r = rnd.nextInt(serchData.size());

			String sData = serchData.get(r);
			//System.out.println(sData);
			int ret = -1;
			int left = 0;
			int right = postList.size() - 1;
			while (left <= right) {
				int mid = (left + right) / 2;
				
				String postaddr = postList.get(mid).getAddr();
				if (sData.equals(postaddr)) {
					ret =  mid + 1;
					//System.out.println(mid);
					break;
				} else if (sData.compareTo(postaddr)< 0) {
					right = mid - 1;
				} else {
					left = mid + 1;
				}
			}
		}
		// 時刻測定
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("ArgoSearchBinary:" + totalTime);
	}
	
	static void HashMapSerch() {
		
		long chgstartTime = System.currentTimeMillis();
		Map<String, Integer> PostMap = new HashMap<String, Integer>(); 
		PostMap = postList.stream().collect(Collectors.toMap(PostInf::getAddr, PostInf::getPostNo));
		
		// 時刻取得
		long startTime = System.currentTimeMillis();

		Random rnd = new Random();


		for (int i = 0; i < serchCnt; i++) {

			int r = rnd.nextInt(serchData.size());

			String sData = serchData.get(r);
			int postaddr = PostMap.get(sData);
		}
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("hashmap:" + totalTime);
		totalTime  = endTime - chgstartTime;
		System.out.println("hashmap + chenge:" + totalTime);
	}
	
	static void TreeMapSerch() {
		
		long chgstartTime = System.currentTimeMillis();
		
		Map<String, Integer> PostMap = new TreeMap<String, Integer>(); 
		PostMap = postList.stream().collect(Collectors.toMap(PostInf::getAddr, PostInf::getPostNo));
		
		// 時刻取得
		long startTime = System.currentTimeMillis();

		Random rnd = new Random();


		for (int i = 0; i < serchCnt; i++) {

			int r = rnd.nextInt(serchData.size());

			String sData = serchData.get(r);
			int postaddr = PostMap.get(sData);
		}
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Treemap:" + totalTime);
		totalTime  = endTime - chgstartTime;
		System.out.println("Treemap + chenge:" + totalTime);
	}
	
	static void LinkedHashMapSerch() {
		
		long chgstartTime = System.currentTimeMillis();
		
		Map<String, Integer> PostMap = new LinkedHashMap<String, Integer>(); 
		PostMap = postList.stream().collect(Collectors.toMap(PostInf::getAddr, PostInf::getPostNo));
		
		// 時刻取得
		long startTime = System.currentTimeMillis();

		Random rnd = new Random();


		for (int i = 0; i < serchCnt; i++) {

			int r = rnd.nextInt(serchData.size());

			String sData = serchData.get(r);
			int postaddr = PostMap.get(sData);
		}
		
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("LinkedHashMap:" + totalTime);
		totalTime  = endTime - chgstartTime;
		System.out.println("LinkedHashMap + chenge:" + totalTime);
	}

}
