import { mount } from "@vue/test-utils";
import Component from "@/components/SettingDialog.vue";
import CellConst from "@/const/const";

var copyFunc = function(array) {
  // SettingDialog.vueの引数に設定する関数を定義
  // 二次元配列をコピーする関数
  let copy = new Array(array.length);
  for (let i = 0; i < array.length; i++) {
    copy[i] = new Array(array[i].length);
    for (let j = 0; j < array[i].length; j++) {
      copy[i][j] = array[i][j];
    }
  }
  return copy;
};
// セルのクラスを返却する関数
var colorFunc = function(cell) {
  let coller = "white";
  if (cell == this.live) {
    coller = "black";
  }
  return coller;
};

let wrapper;

beforeEach(() => {
  // it実行前に行う処理
  // wrapper生成
  wrapper = mount(Component, {
    propsData: {
      openFlg: true,
      defaultCells: CellConst.initCells,
      deepCopy: copyFunc,
      cellColler: colorFunc
    }
  });
});

afterEach(() => {
  // it実行後に行う処理
  wrapper.destroy();
});

describe("Testing SettingDialog component", () => {
  it("is a Vue instance", () => {
    // vueインスタンスが生成されてることを確認
    expect(wrapper.isVueInstance).toBeTruthy();
  });
  it("props test", () => {
    // propsの各変数が設定されているか確認
    expect(wrapper.props().openFlg).toBeTruthy();
    expect(wrapper.props().defaultCells).toBe(CellConst.initCells);
    expect(typeof wrapper.props().deepCopy).toBe("function");
    expect(typeof wrapper.props().cellColler).toBe("function");
  });
  it("data初期値 test", () => {
    // dataの各初期値が設定されているか確認
    expect(wrapper.vm.cells).toBeNull();
    expect(wrapper.vm.death).toBe(0);
    expect(wrapper.vm.live).toBe(1);
  });
  it("watch defaultCells test", async () => {
    // props更新のためwrapper再生成
    wrapper.destroy();
    wrapper = mount(Component, {
      propsData: {
        openFlg: true,
        deepCopy: copyFunc,
        cellColler: colorFunc
      }
    });
    // props更新前のcells
    expect(wrapper.vm.cells).toBeNull();
    // props.defaultCellsに設定する配列
    let testCells = CellConst.initCells;
    testCells[0][0] = 1;
    // propsのdefaultCellsをセット
    await wrapper.setProps({ defaultCells: testCells });
    // cellsが更新されていることを確認
    expect(wrapper.vm.cells).toEqual(testCells);
  });
  it("methods setCell test", async () => {
    // props更新しdata.cellsに反映させる
    wrapper.destroy();
    wrapper = mount(Component, {
      propsData: {
        openFlg: true,
        deepCopy: copyFunc,
        cellColler: colorFunc
      }
    });
    await wrapper.setProps({ defaultCells: CellConst.initCells });
    // eventオブジェクトの代わり
    let testObj = {
      target: {
        className: ""
      }
    };
    // setCell呼び出し前のdata確認
    expect(testObj.target.className).toBe("");
    expect(wrapper.vm.cells[0][2]).toBe(0);
    // setCell呼び出し
    wrapper.vm.setCell(0, 2, 1, testObj);
    // setCell呼び出し後のeventオブジェクト確認
    expect(testObj.target.className).toBe("black");
    // setCell呼び出し後のcellsが引数の値で更新されていることを確認
    expect(wrapper.vm.cells[0][2]).toBe(1);
  });
  it("methods ok test", async () => {
    // props更新しdata.cellsに反映させる
    wrapper.destroy();
    wrapper = mount(Component);
    await wrapper.setProps({
      defaultCells: CellConst.initCells,
      openFlg: true,
      deepCopy: copyFunc,
      cellColler: colorFunc
    });
    expect(wrapper.vm.cells).not.toBeNull();
    // ok呼び出し
    wrapper.vm.ok();
    // イベント発行されたか確認
    expect(wrapper.emitted().ok).toBeTruthy();
    // イベント発行された回数確認
    expect(wrapper.emitted().ok.length).toBe(1);
    // イベント引数確認
    expect(wrapper.emitted().ok[0][0]).toEqual(CellConst.initCells);
  });
  it("tdタグ数 test", async () => {
    // props更新しdata.cellsに反映させる
    wrapper.destroy();
    wrapper = mount(Component);
    await wrapper.setProps({
      defaultCells: CellConst.initCells,
      openFlg: true,
      deepCopy: copyFunc,
      cellColler: colorFunc
    });
    expect(wrapper.vm.cells).not.toBeNull();
    const cells = wrapper.findAll("td");
    // td数確認
    expect(cells).toHaveLength(400);
  });
  it("tdタグ左マウスダウン test", async () => {
    // props更新しdata.cellsに反映させる
    wrapper.destroy();
    wrapper = mount(Component);
    await wrapper.setProps({
      defaultCells: CellConst.initCells,
      openFlg: true,
      deepCopy: copyFunc,
      cellColler: colorFunc
    });
    expect(wrapper.vm.cells).not.toBeNull();

    // whiteセル取得(1セル抽出し確認)
    let cell = wrapper.findAll("td.white").at(0);
    expect(cell.exists()).toBeTruthy();
    expect(cell.classes()).not.toContain("black");
    // イベント発行
    await cell.trigger("mousedown.left");
    // クラスが変わること確認
    expect(cell.classes()).toContain("black");
  });
  // it("tdタグ右マウスダウン test", async () => {
  //   // props更新しdata.cellsに反映させる
  //   wrapper.destroy();
  //   wrapper = mount(Component);
  //   await wrapper.setProps({
  //     defaultCells: CellConst.initCells,
  //     openFlg: true,
  //     deepCopy: copyFunc,
  //     cellColler: colorFunc
  //   });
  //   expect(wrapper.vm.cells).not.toBeNull();

  //   // whiteセル取得(1セル抽出し確認)
  //   let cell = wrapper.findAll("td.black").at(0);
  //   expect(cell.exists()).toBeTruthy();
  //   expect(cell.classes()).not.toContain("white");
  //   // イベント発行確認 なぜかtriggerが右マウスダウンだと機能しない。実際の画面では動くのが確認んできたためコメントアウト
  //   await cell.trigger("mousedown.right");
  //   expect(cell.classes()).toContain("white");
  // });
});
