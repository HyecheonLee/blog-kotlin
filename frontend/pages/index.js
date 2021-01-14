import React from 'react';
import {getSortedPostsData} from '../lib/posts'
import Link from 'next/link'

export default function Home({allPostsData}) {
	return (
		<>
			<h2>IndexPage</h2>
			<Link href="/user/singUp">
				<a>SingUp</a>
			</Link>
			<Link href="/user/singIn">
				<a>SingIn</a>
			</Link>
		</>
	)
}

export async function getStaticProps() {
	const allPostsData = getSortedPostsData()
	return {
		props: {
			allPostsData
		}
	}
}
