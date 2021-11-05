<template>
  <v-container>
    <div>
      <span>-- 第 {{ generation }} 世代 --</span>
    </div>
    <table class="cell-table">
      <tbody>
        <tr v-for="(item, i) in cells" :key="'row' + i" class="ma-0 pa-0">
          <td
            v-for="(cell, j) in item"
            :key="'cell' + j"
            v-bind:class="cellColler(cell)"
          ></td>
        </tr>
      </tbody>
    </table>

    <v-row class="ma-auto">
      <v-col>
        <v-btn @click="start" v-if="stopFlg == true" color="blue" raised rounded dark>
          <span class="mr-2">スタート</span>
          <v-icon>mdi-clock-start</v-icon>
        </v-btn>
        <v-btn @click="stop" v-if="stopFlg == false" color="blue darken-4" raised rounded dark>
          <span class="mr-2">ストップ</span>
          <v-icon>mdi-stop-circle-outline</v-icon>
        </v-btn>
      </v-col>
      <v-col>
        <v-btn @click="clear" v-bind:disabled="!stopFlg" color="blue lighten-5">
          <span class="mr-2">クリア</span>
          <v-icon>mdi-notification-clear-all</v-icon>
        </v-btn>
      </v-col>
      <v-col>
        <v-btn @click="dialog = true" v-bind:disabled="!stopFlg" color="blue lighten-5">
          <span class="mr-2">セル配置設定</span>
          <v-icon>mdi-table-settings</v-icon>
        </v-btn>
        <SettingDialog
          v-bind:defaultCells="deepCopy(cells)"
          v-bind:openFlg="dialog"
          :deepCopy="deepCopy"
          :cellColler="cellColler"
          @ok="
            cells = $event;
            dialog = false;
          "
        />
      </v-col>
    </v-row>
  </v-container>
</template>
<style scoped lang="css">
.cell-table {
  margin: 0 0;
  border: 2px solid #999;
}
.cell-table td {
  border: 1px solid #999;
  height: 20px;
  width: 20px;
  text-align: center;
  vertical-align: middle;
  display: table-cell;
}
</style>

<script>
import CellConst from "../const/const";
import SettingDialog from "../components/SettingDialog";

export default {
  name: "LifeGame",
  components: {
    SettingDialog,
  },
  data: () => ({
    cells: JSON.parse(JSON.stringify(CellConst.initCells)),    // セルのデータ
    death: CellConst.death,   // 死滅：0
    live: CellConst.live,   // 生存：1
    maxLength: CellConst.maxLength,   // セル配列の最大長
    stopFlg: true,   // ストップしているかのフラグ
    generation: 0,   // 世代数
    nextCells: JSON.parse(JSON.stringify(CellConst.initCells)),   // 次世代仮置き用
    timerId: 0,   // タイマーID
    dialog: false,   // ダイアログ表示フラグ
  }),
  methods: {
    deepCopy: function(array) {
      // 引数の二次元配列の値をディープコピーする
      let copy = new Array(array.length);
      for (let i = 0; i < array.length; i++) {
        copy[i] = new Array(array[i].length);
        for (let j = 0; j < array[i].length; j++) {
          copy[i][j] = array[i][j];
        }
      }
      // コピーした二次元配列を返却
      return copy;
    },
    cellColler: function(cell) {
      // 値を判定してセルのクラス名を返す
      let coller = "white";
      if (cell == this.live) {
        coller = "black";
      }
      return coller;
    },
    judgeCell: function(CellX, CellY) {
      // セルの生死判定を行う
      // 引数は判定対象のセルのインデックス
      let result = 0;
      // 座標
      let x;
      let y;
      // 生きてるセルの数
      let count = 0;
      // 周囲の生きているセルの数を数える
      for (let i = -1; i < 2; i++) {
        for (let j = -1; j < 2; j++) {
          x = CellX + i;
          y = CellY + j;
          if (0 <= x && x < this.maxLength && 0 <= y && y < this.maxLength) {
            if (this.cells[x][y] == this.live && (i != 0 || j != 0)) {
              // 生きていたらカウントアップ（自分自身はカウントしない）
              count++;
            }
          }
        }
      }

      // 対象セルの生死、周囲の生存セル数から次世代判定
      if (this.cells[CellX][CellY] == this.death) {
        // 自分が死んでる場合
        if (count == 3) {
          // 誕生
          result = this.live;
        } else {
          // 死んだまま
          result = this.death;
        }
      } else {
        // 自分が生きてる場合
        if (count >= 4) {
          // 過密
          result = this.death;
        } else if (count == 2 || count == 3) {
          // 生存維持
          result = this.live;
        } else if (count <= 1) {
          // 過疎
          result = this.death;
        }
      }
      return result;
    },
    execute: function() {
      // 各セルの生死を判定
      for (let i = 0; i < this.maxLength; i++) {
        for (let j = 0; j < this.maxLength; j++) {
          this.nextCells[i][j] = this.judgeCell(i, j);
        }
      }
      // 次世代データをコピーし画面に反映する
      this.cells = this.deepCopy(this.nextCells);
      // 世代カウントアップ
      this.generation++;
    },
    start: function() {
      let vc = this;
      // タイマーを100ミリ秒間隔で設定
      this.timerId = setTimeout(function() {
        vc.stopFlg = false;
        // 処理を行う
        vc.execute();
        // 再起呼び出し
        vc.start();
      }, 100);
    },
    stop: function() {
      // タイマーストップ
      clearTimeout(this.timerId);
      this.stopFlg = true;
    },
    clear: function() {
      // クリア
      this.cells = new Array(this.maxLength);
      for (var i = 0; i < this.maxLength; i++) {
        this.cells[i] = new Array(this.maxLength).fill(0);
      }
      this.nextCells = this.deepCopy(this.cells);
      this.generation = 0;
    },
  },
};
</script>
