import { mount } from '@vue/test-utils';
import Component from '@/App.vue'

describe('Testing App component', () => {
    it('is a Vue instance', () => {
      // vueインスタンスが生成されてることを確認
      const wrapper = mount(Component)
      expect(wrapper.isVueInstance).toBeTruthy()
    })
})