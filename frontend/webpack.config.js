"use strict";
var webpack = require('webpack');

var HtmlWebpackPlugin = require('html-webpack-plugin');

const HOST = process.env.HOST || "127.0.0.1";
const PORT = process.env.PORT || "8081";

module.exports = {
	entry: './src/app.js',
	output: {
		path: './target/classes/public',
		filename: 'bundle.js'
	},
	resolve: {
		extensions: ['', '.js', '.css', '.scss']
	},
	module: {
		loaders: [
			{
				test: /\.jsx?$/,
				exclude: /(node_modules|bower_components|public)/,
				loader: 'babel',
				query: {
					presets: ['es2015', 'stage-0', 'react'],
					plugins: ['transform-runtime', 'transform-decorators-legacy', 'transform-class-properties'],
				}
			},
			{
				test: /[\/\\]src[\/\\].*\.scss/,
				exclude: /(node_modules|bower_components|public)/,
				loaders: [
					'style?sourceMap',
					'css',
					'sass'
				]
			},
			{
				test: /[\/\\]src[\/\\].*\.css/,
				exclude: /(node_modules|bower_components|public)/,
				loaders: [
					'style?sourceMap',
					'css?modules&importLoaders=1&localIdentName=[path]___[name]__[local]___[hash:base64:5]'
				]
			},
            { test: /\.css$/, loader: 'style-loader!css-loader' }
        ]
	},
	devServer: {
		contentBase: "./target/classes/public",
		noInfo: true,
		hot: true,
		inline: true,
		historyApiFallback: true,
		port: PORT,
		host: HOST
	},
	plugins: [
		new webpack.NoErrorsPlugin(),
		new webpack.HotModuleReplacementPlugin(),
		new HtmlWebpackPlugin({
			template: './src/index.html'
		}),
		new webpack.SourceMapDevToolPlugin(),
	]
};
