import Vue from 'vue'
import { config } from '@vue/test-utils'

Vue.config.silent = true

//  console.error
//  [Vue warn]: Unknown custom element: ～の警告が出るため、
//  JestがVuetifyコンポーネントを認識できるように、警告が出たタグのモックを作成する
config.stubs['v-btn'] = { template: "<button><slot /></button>"};
