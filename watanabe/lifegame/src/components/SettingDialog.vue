<template>
  <v-container>
    <v-dialog v-model="openFlg">
      <v-card>
        <v-card-title>セル配置設定</v-card-title>
        <v-divider></v-divider>
        <table class="cell-table">
          <tbody>
            <tr v-for="(item, i) in cells" :key="'row' + i" class="ma-0 pa-0">
              <td
                v-for="(cell, j) in item"
                :key="'cell' + j"
                v-bind:class="cellColler(cell)"
                @mousedown.left="setCell(i, j, live, $event)"
                @mousedown.right="setCell(i, j, death, $event)"
                @contextmenu.prevent="false"
              ></td>
            </tr>
          </tbody>
        </table>
        <v-divider></v-divider>
        <v-card-actions>
            <div >
            左クリック：生存
            <br>
            右クリック：死滅
            </div>
          <v-btn @click="ok" absolute right color="primary">
            <span class="mr-2">OK</span>
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>
<style scoped lang="css">
.cell-table {
  margin: auto;
  border: 2px solid #999;
}
.cell-table td {
  border: 1px solid #999;
  height: 20px;
  width: 20px;
  text-align: center;
  vertical-align: middle;
  cursor: pointer;
  display: table-cell;
}
</style>

<script>
import CellConst from "../const/const";

export default {
  name: "SettingDialog",
  props: {
    // 当コンポーネントの引数
    // ダイアログ表示フラグ
    openFlg: {
      type: Boolean,
      defult: false,
      required: true,
    },
    // セル配列
    defaultCells: {
      type: Array,
      defult: new Array(CellConst.maxLength),
      required: true,
    },
    // セル配列をコピーするための関数（親コンポーネントと共通のため引数化）
    deepCopy: {
      type: Function,
      required: true,
    },
    // セルのクラスを判定するための関数（親コンポーネントと共通のため引数化）
    cellColler: {
      type: Function,
      required: true,
    },
  },
  data: () => ({
    cells: null,  // 設定用セル
    death: CellConst.death,  // 死滅：0
    live: CellConst.live,  // 生存：1
  }),
  watch: {
    defaultCells: {
      handler() {
        // defaultCellsの値が変化した場合、cellsにコピーする
        this.cells = this.deepCopy(this.defaultCells);
      },
      deep: true,
    },
  },
  methods: {
    setCell: function(x, y, val, event) {
      // cellsに値（生死）を設定する
      this.cells[x][y] = val;
      // 生死に応じてクラス名を設定する
      event.target.className = this.cellColler(val);
    },
    ok: function() {
      // okイベントを発火し、cellsを親コンポーネントに渡す
      this.$emit("ok", this.cells);
    },
  },
};
</script>
