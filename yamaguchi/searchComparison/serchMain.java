package searchComparison;

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
	static ArrayList<Integer> serchData;
	
	static private int serchCnt;

	public static void main(String[] args) {

		postList = new ArrayList<PostInf>();
		serchData = new ArrayList<Integer>();
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
				serchData.add(number);
				StringBuffer buf = new StringBuffer();
				for (int i = 1; i < data.length; i++) {
					buf.append(data[i]);
				}
				String s = buf.toString();
				PostInf postData = new PostInf(number, s);
				postList.add(postData);
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

			int sData = serchData.get(r);

			for (int iLoop = 0; iLoop < postList.size(); iLoop++) {
				int postNo = postList.get(iLoop).getPostNo();
				if (postNo == sData) {
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

			int sData = serchData.get(r);
			boolean matchFlg = false;
			for (int iLoop = startPos; iLoop < postList.size(); iLoop++) {
				int postNo = postList.get(iLoop).getPostNo();
				if (postNo == sData) {
					startPos = iLoop;
					matchFlg = true;
					//System.out.println(postList.get(iLoop).getAddr());
					break;
				}
			}
			if (!matchFlg) {
				for (int iLoop = 0; iLoop < startPos; iLoop++) {
					int postNo = postList.get(iLoop).getPostNo();
					if (postNo == sData) {
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

			int sData = serchData.get(r);
			//System.out.println(sData);
			int ret = -1;
			int left = 0;
			int right = postList.size() - 1;
			while (left <= right) {
				int mid = (left + right) / 2;
				
				int postNo = postList.get(mid).getPostNo();
				if (postNo == sData) {
					ret =  mid + 1;
					//System.out.println(mid);
					break;
				} else if (sData < postNo) {
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
		Map<Integer, String> PostMap = new HashMap<Integer, String>(); 
		PostMap = postList.stream().collect(Collectors.toMap(PostInf::getPostNo, PostInf::getAddr));
		
		// 時刻取得
		long startTime = System.currentTimeMillis();

		Random rnd = new Random();


		for (int i = 0; i < serchCnt; i++) {

			int r = rnd.nextInt(serchData.size());

			int sData = serchData.get(r);
			String addr = PostMap.get(sData);
		}
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("hashmap:" + totalTime);
		totalTime  = endTime - chgstartTime;
		System.out.println("hashmap + chenge:" + totalTime);
	}
	
	static void TreeMapSerch() {
		
		long chgstartTime = System.currentTimeMillis();
		Map<Integer, String> PostMap = new TreeMap<Integer, String>(); 
		PostMap = postList.stream().collect(Collectors.toMap(PostInf::getPostNo, PostInf::getAddr));
		
		// 時刻取得
		long startTime = System.currentTimeMillis();

		Random rnd = new Random();


		for (int i = 0; i < serchCnt; i++) {

			int r = rnd.nextInt(serchData.size());

			int sData = serchData.get(r);
			String addr = PostMap.get(sData);
		}
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Treemap:" + totalTime);
		totalTime  = endTime - chgstartTime;
		System.out.println("Treemap + chenge:" + totalTime);
	}
	
	static void LinkedHashMapSerch() {
		
		long chgstartTime = System.currentTimeMillis();
		Map<Integer, String> PostMap = new LinkedHashMap<Integer, String>(); 
		PostMap = postList.stream().collect(Collectors.toMap(PostInf::getPostNo, PostInf::getAddr));
		
		// 時刻取得
		long startTime = System.currentTimeMillis();

		Random rnd = new Random();


		for (int i = 0; i < serchCnt; i++) {

			int r = rnd.nextInt(serchData.size());

			int sData = serchData.get(r);
			String addr = PostMap.get(sData);
			//System.out.println("LinkedHashMap:" + addr);
		}
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("LinkedHashMap:" + totalTime);
		totalTime  = endTime - chgstartTime;
		System.out.println("LinkedHashMap + chenge:" + totalTime);
	}
}
