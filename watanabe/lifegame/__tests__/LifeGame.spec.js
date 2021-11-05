import { mount } from "@vue/test-utils";
import Component from "@/components/LifeGame.vue";
import SettingDialog from "@/components/SettingDialog.vue";
import CellConst from "@/const/const";

describe("Testing LifeGame component", () => {
  let wrapper;

  beforeEach(() => {
    wrapper = mount(Component);
  });

  afterEach(() => {
    jest.useRealTimers();
    wrapper.destroy();
  });

  it("is a Vue instance", () => {
    expect(wrapper.isVueInstance).toBeTruthy();
  });
  it("components test", () => {
    const childComponent = wrapper.findComponent(SettingDialog);
    expect(childComponent.exists()).toBe(true);
    const childComponents = wrapper.findAllComponents(SettingDialog);
    expect(childComponents).toHaveLength(1);
  });
  it("data初期値 test", () => {
    expect(wrapper.vm.cells).toEqual(CellConst.initCells);
    expect(wrapper.vm.death).toBe(0);
    expect(wrapper.vm.live).toBe(1);
    expect(wrapper.vm.maxLength).toBe(20);
    expect(wrapper.vm.stopFlg).toBeTruthy();
    expect(wrapper.vm.generation).toBe(0);
    expect(wrapper.vm.nextCells).toEqual(CellConst.initCells);
    expect(wrapper.vm.timerId).toBe(0);
    expect(wrapper.vm.dialog).not.toBeTruthy();

    let whiteCells = wrapper.findAll("td.black");
    expect(whiteCells).toHaveLength(22);
    let blackCells = wrapper.findAll("td.white");
    expect(blackCells).toHaveLength(378);
  });
  it("@ok event test", async () => {
    expect(wrapper.vm.cells).toEqual(CellConst.initCells);
    const array = new Array(20);
    for (var i = 0; i < array.length; i++) {
      array[i] = new Array(20).fill(0);
    }
    await wrapper.setData({ dialog: true });
    wrapper.findComponent(SettingDialog).vm.$emit("ok", array);
    expect(wrapper.vm.cells).toEqual(array);
    expect(wrapper.vm.dialog).not.toBeTruthy();
  });
  it("methods deepCopy test", () => {
    // テスト用配列作成
    let dummy = new Array(3);
    dummy[0] = new Array(5);
    dummy[0][0] = 0;
    dummy[0][1] = 1;
    dummy[0][2] = 2;
    dummy[0][3] = 3;
    dummy[0][4] = 4;
    dummy[1] = new Array(2);
    dummy[1][0] = 6;
    dummy[1][1] = 5;
    dummy[2] = new Array(1);
    dummy[2][0] = 7;
    // 関数作成
    const result = wrapper.vm.deepCopy(dummy);
    expect(result).toEqual(dummy);
  });
  it("methods cellColler test", () => {
    // 0(死滅)の場合
    const result1 = wrapper.vm.cellColler(0);
    expect(result1).toBe("white");

    // 1(生存)の場合
    const result2 = wrapper.vm.cellColler(1);
    expect(result2).toBe("black");
  });
  it("methods execute test", () => {
    // データ
    expect(wrapper.vm.cells).toEqual(CellConst.initCells);
    expect(wrapper.vm.nextCells).toEqual(CellConst.initCells);
    // 世代カウント
    expect(wrapper.vm.generation).toBe(0);
    // 実行
    wrapper.vm.execute();
    // 次世代データ
    let actual = [
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    ];
    expect(wrapper.vm.cells).toEqual(actual);
    expect(wrapper.vm.nextCells).toEqual(actual);
    // 世代カウント
    expect(wrapper.vm.generation).toBe(1);
  });
  it("methods start test", () => {
    jest.useFakeTimers();
    jest.spyOn(global, "setTimeout");
    wrapper.vm.start();
    // timerの確認
    expect(setTimeout).toHaveBeenCalledTimes(1);
    expect(setTimeout).toHaveBeenLastCalledWith(expect.any(Function), 100);
    jest.clearAllTimers();
  });
  it("methods stop test", () => {
    jest.useFakeTimers();
    jest.spyOn(global, "setTimeout");
    jest.spyOn(global, "clearTimeout");
    wrapper.vm.start();
    wrapper.vm.stop();
    // timerの確認
    expect(setTimeout).toHaveBeenCalledTimes(1);
    expect(clearTimeout).toHaveBeenCalledTimes(1);
    // stopFlgの確認
    expect(wrapper.vm.stopFlg).toBe(true);
  });
  it("methods clear test", async () => {
    // set data
    await wrapper.setData({
      cells: CellConst.initCells,
      nextCells: CellConst.initCells,
      generation: 50
    });
    // 実行
    wrapper.vm.clear();
    const actual = [
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    ];
    expect(wrapper.vm.cells).toEqual(actual);
    expect(wrapper.vm.nextCells).toEqual(actual);
    expect(wrapper.vm.generation).toBe(0);
  });
  it("methods judgeCell test", async () => {
    // set data
    await wrapper.setData({
      cells: CellConst.initCells,
      nextCells: CellConst.initCells
    });

    // 試験パターン
    // 端っこのセル
    expect(wrapper.vm.cells[0][0]).toBe(0);
    const test1 = wrapper.vm.judgeCell(0, 0);
    expect(test1).toBe(0);

    // 自分が死んでる場合　誕生
    expect(wrapper.vm.cells[5][7]).toBe(0);
    const test2 = wrapper.vm.judgeCell(5, 7);
    expect(test2).toBe(1);

    // 自分が死んでる場合　死んだまま
    expect(wrapper.vm.cells[7][7]).toBe(0);
    const test3 = wrapper.vm.judgeCell(7, 7);
    expect(test3).toBe(0);

    // 自分が生きてる場合　過密
    expect(wrapper.vm.cells[7][8]).toBe(1);
    const test4 = wrapper.vm.judgeCell(7, 8);
    expect(test4).toBe(0);

    // 自分が生きてる場合　生存維持
    expect(wrapper.vm.cells[6][6]).toBe(1);
    const test5 = wrapper.vm.judgeCell(6, 6);
    expect(test5).toBe(1);

    // 自分が生きてる場合　過疎
    // set data
    await wrapper.setData({
      cells: [
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
      ],
      nextCells: CellConst.initCells
    });
    expect(wrapper.vm.cells[1][3]).toBe(1);
    const test6 = wrapper.vm.judgeCell(1, 3);
    expect(test6).toBe(0);
  });
});
