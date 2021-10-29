const death = 0;
const live = 1;
// セル初期値（20×20）列と行が同じ数なら20以上も設定可能
const initCells = [
    [death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death],
    [death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death],
    [death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death],
    [death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death],
    [death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death],
    [death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death],
    [death,death,death,death,death,death,live,live,live,live,live,live,live,live,death,death,death,death,death,death],
    [death,death,death,death,death,death,live,death,live,live,live,live,death,live,death,death,death,death,death,death],
    [death,death,death,death,death,death,live,live,live,live,live,live,live,live,death,death,death,death,death,death],
    [death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death],
    [death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death],
    [death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death],
    [death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death],
    [death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death],
    [death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death],
    [death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death],
    [death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death],
    [death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death],
    [death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death],
    [death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death,death]
];
const maxLength = initCells.length;
export default {
    initCells,
    death,
    live,
    maxLength
};