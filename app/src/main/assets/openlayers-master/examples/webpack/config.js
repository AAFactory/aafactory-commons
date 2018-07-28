const CopyPlugin = require('copy-webpack-plugin');
const ExampleBuilder = require('./example-builder');
const fs = require('fs');
const path = require('path');

const src = path.join(__dirname, '..');

const examples = fs.readdirSync(src)
  .filter(name => /^(?!index).*\.html$/.test(name))
  .map(name => name.replace(/\.html$/, ''));

const entry = {};
examples.forEach(example => {
  entry[example] = `./${example}.js`;
});
module: {
  rules: [
    { parser: { amd: false } }
  ]
}
module.exports = {
  context: src,
  target: 'web',
  entry: entry,
  optimization: {
       minimize: false,
    runtimeChunk: {
      name: 'common'
    },
    splitChunks: {
      name: 'common',
      chunks: 'initial',
      minChunks: 2
    }
  },
  plugins: [
    new ExampleBuilder({
      templates: path.join(__dirname, '..', 'templates'),
      common: 'common'
    }),
    new CopyPlugin([
      {from: '../src/ol/ol.css', to: 'css'},
      {from: 'data', to: 'data'},
      {from: 'resources', to: 'resources'},
      {from: 'Jugl.js', to: 'Jugl.js'},
      {from: 'index.html', to: 'index.html'}
    ])
  ],
  devtool: 'source-map',
  output: {
    filename: '[name].js',
    path: path.join(__dirname, '..', '..', 'build', 'examples')
  }
};
