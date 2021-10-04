import React from 'react';
import renderer from 'react-test-renderer';
import App from '../App';

// Appコンポーネントのテスト
it('Page snapshot testing', () => {
  // React要素のインスタンスを作成
  const component = renderer.create(<App />);
  // 最新スナップショットと比較
  expect(component).toMatchSnapshot();
});