import { shallowMount  } from '@vue/test-utils';
import Component from '@/views/Home.vue'

describe('Testing Home component', () => {
    it('is a Vue instance', () => {
      // vueインスタンスが生成されてることを確認
      const wrapper = shallowMount(Component)
      expect(wrapper.isVueInstance).toBeTruthy()
    });
})