const path = require('path')
const AutoImport = require('unplugin-auto-import/webpack')
const Components = require('unplugin-vue-components/webpack')
const { ElementPlusResolver } = require('unplugin-vue-components/resolvers')

module.exports = {
    publicPath: "/web/",
    chainWebpack: config => {
      //svg 配置
      config.module.rules.delete("svg"); //重点：删除默认配置中处理 svg
      config.module.rule('svg-sprite-loader').test(/\.svg$/)
        .include
        .add(path.resolve('./src/assets/svg')) //处理 svg 保存路径
        .end()
        .use('svg-sprite-loader')
        .loader('svg-sprite-loader')
        .options({
          symbolId: 'icon-[name]'  //给 symbo 配置 id
        })
    },
    configureWebpack: {
        plugins: [
            AutoImport({
                resolvers: [ElementPlusResolver()]
            }),
            Components({
                resolvers: [ElementPlusResolver()]
            })
        ]
    }
}
