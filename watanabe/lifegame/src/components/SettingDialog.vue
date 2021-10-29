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
    openFlg: {
      type: Boolean,
      defult: false,
      required: true,
    },
    defaultCells: {
      type: Array,
      defult: new Array(CellConst.maxLength),
      required: true,
    },
  },
  data: () => ({
    cells: null,
    death: CellConst.death,
    live: CellConst.live,
  }),
  watch: {
    defaultCells: {
      handler() {
        this.cells = this.deepCopy(this.defaultCells);
      },
      deep: true,
    },
  },
  methods: {
    setCell: function(x, y, val, event) {
      this.cells[x][y] = val;
      event.target.className = this.cellColler(val);
    },
    cellColler: function(cell) {
      let coller = "white";
      if (cell == this.live) {
        coller = "black";
      }
      return coller;
    },
    deepCopy: function(array) {
      let copy = new Array(array.length);
      for (let i = 0; i < array.length; i++) {
        copy[i] = new Array(array[i].length);
        for (let j = 0; j < array[i].length; j++) {
          copy[i][j] = array[i][j];
        }
      }
      return copy;
    },
    ok: function() {
      this.$emit("ok", this.cells);
    },
  },
};
</script>
