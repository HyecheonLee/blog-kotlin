import '../styles/global.css'
import React from "react";
import Head from 'next/head'
import Layout from "../components/Layout";
import {RecoilRoot} from "recoil";


export default function App({Component, pageProps}) {
	return (
		<>
			<Head>
				<meta charSet="utf-8"/>
				<meta name="viewport" content="width=device-width, initial-scale=1"/>
				<meta name="description" content="hyecheon lee web"/>
				<title>hyecheonlee web</title>
				<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>
			</Head>
			<RecoilRoot>
				<Layout>
					<Component {...pageProps} />
				</Layout>
			</RecoilRoot>
		</>
	)
}
