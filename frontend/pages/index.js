import React from 'react';
import {getSortedPostsData} from '../lib/posts'
import Link from 'next/link'
import {API} from "../config";
import axios from "axios";

export default function Home({allPostsData}) {
	return (
		<>
			<h2>IndexPage</h2>
			<div>
				<Link href="/user/signUp">
					<a>회원가입</a>
				</Link>
			</div>
			<div>
				<Link href="/user/signIn">
					<a>로그인</a>
				</Link>
			</div>
			<button onClick={e => {
				axios.get(`${API}/api/v1/test`).then(response => {
					console.log(response)
				}).catch(e => console.log(e.response));
			}}>테스트
			</button>
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
