#include <stdio.h>
#include <stdlib.h>
#include <string.h>
 
 /*プロトタイプ宣言*/
void print_word(char str[][50], int cnt);
void search_word(char str[][50], int cnt);
void sort(char *str, int str_len);
void check_anagram(char str[][50], int cnt);

 
int main(int argc, char** argv) {
	
    FILE *file;
	char s[512];
    char* str = s;
	char word[10][50];
	int cnt = 0;
	char end = 0;
	char scan_m;
	int mode = 0;
	
    if (argc <= 1)
	{
		printf("読み込みファイル名を指定してください。 ex) ./word_analyser file.csv\n");
		return 0;
    }

    file = fopen(argv[1], "r");
    if (NULL == file) {
        printf("ファイルが存在しません。\n");

		return 0;
    }

    fgets(str, 512, file);
	fclose(file);
	
	// 最初の","を探す
	str = strtok(str, ",");
	while (str) {
        //単語を配列に保持
		strcpy(word[cnt], str);
		cnt++;

		// 次の","を探す
		str = strtok(NULL, ",");
		if (str == NULL ) {
			// ","がない場合、最後の"を探す
			str = strtok(NULL, "\n");
		}

		//10個以上記載があっても10個目以降は無視する。
		if (10 == cnt) {
			break;
		}
	}

	while (!end) {
		printf(
			"-----------------------\n"
			"◆文字解析プログラム◆\n"  
			"モードを選択してください。\n"  
			"  1. 読み込み単語表示\n"
			"  2. 文字検索\n"
			"  3. アナグラムチェック\n"  
			"  4. 終了\n"  
			"-----------------------\n"
		);

		//モードをキーボード入力
		printf(">>");
		scanf("%c",&scan_m);
		getchar();	//改行文字の空読み
		printf("\n");
		mode = atoi(&scan_m);

		//モード毎の処理を実施
		switch (mode) {
		case 1:
			//単語表示
			print_word(word, cnt);
			break;

		case 2:
			//文字検索
			search_word(word, cnt);
			break;

		case 3:
			//アナグラムチェック
			check_anagram(word, cnt);
			break;

		case 4:

			//終了
			end = 1;
			break;

		default:
			printf("1～4でモードを選択してください。\n");
			break;
		}
	}

	printf("program end\n");
	return 0;
}


/*単語表示関連*/
void print_word(char str[][50], int cnt){
	int i;

	//読み込んだ単語の数だけ表示
	printf(
		"■読み込み単語表示\n"
    	"以下の単語を読み込みました。\n"
	);
	for (i=0; i<cnt; i++) {
		printf("%2d. %s\n", i+1, str[i]);
	}
	printf("\n");

	return;
}


/*文字検索関連*/
void search_word(char str[][50], int cnt){
	int i;
	char s_word[50];

	//検索文字をキーボード入力
	printf(
		"■文字検索\n"
    	"検索したい文字を1文字入力してください。\n"
	);
	printf(">>");
	scanf("%s", &s_word);
	getchar();	//改行文字の空読み
	
	//検索文字を含む単語を表示
	printf("\n");
	printf("当該文字を含む単語は以下です。\n");
	for (i=0; i<cnt; i++) {
		if (NULL != strstr(str[i], s_word)) {
			printf(" %s\n", str[i]);
		}
	}
	printf("\n");

	return;
}

/*アナグラムチェック関連*/
//チェック関数
void check_anagram(char str[][50], int cnt){
	char str1[50];
	char str2[50];
	int str_len1;
	int str_len2;
	int i,j;
	int flg = 0;

	for (i=0; i<cnt; i++){
		//1単語目から順に取り出し、チェックしていく
		memcpy(str1, &str[i], 50);

		for (j=i+1; j<cnt; j++) {
			memcpy(str2, &str[j], 50);

			/*文字数が同じか確認*/
			str_len1 = strlen(str1);
			str_len2 = strlen(str2);
			if(str_len1 != str_len2){
				//文字数不一致はアナグラムではないのでcontinue
				continue;
			}
			
			/*sort関数で昇順に並び替え*/
			sort(str1, str_len1);
			sort(str2, str_len2);
			
			/*文字列を比較し、同じであればアナグラム*/
			if( 0 == strcmp(str1, str2) ){
				printf("%sと%sはアナグラムです。\n", str[i], str[j]);
				flg = 1;
			}
		}
	}

	if (0 == flg) {
		printf("アナグラムの関係の単語はありません。\n");
	}
	printf("\n");

	return;
}

//入力文字を昇順に並び替える関数
void sort(char *str, int str_len){
	int i,j;
	char tmp;
	
	/*1文字目から順に、それ以降の文字と比較する*/
	for(i=0; i<str_len; i++){
		for(j=i+1; j<str_len; j++){
			/*i番目の文字がj番目の文字より後ろの順序の場合は入れ替える。*/
			if(str[i] > str[j]){
				tmp = str[i];
				str[i] = str[j];
				str[j] = tmp;
			}
		}
	}
	
	return;
}