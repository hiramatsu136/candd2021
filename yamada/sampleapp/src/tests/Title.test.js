import React from 'react';
import { render } from 'react-dom';
import { act } from 'react-dom/test-utils';
import Title from '../components/Title';

describe('Title component testing', () => {
    let container = null;
    container = document.createElement("div");
    document.body.appendChild(container);

    it('render', () => {
        // ケース別にテストします
        act(() => {
            render(<Title />, container);
        });
        // propsが渡されない場合
        expect(container.textContent).toBe('No contents');

        act(() => {
            render(<Title children="this Title"/>, container);
        });
        // propsが渡された場合
        expect(container.textContent).toBe('this Title');
    });
})