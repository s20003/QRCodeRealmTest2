# お薬タイマー

## 概要

薬の飲み忘れを防ぐAndroidアプリ

## 機能

- 時間になると薬を飲んだかの確認の通知を送る(時間を設定する)

- 薬の情報をQRコードで登録できるようにする

- 利用者が薬を飲んだ場合は、「薬を飲んだ」ボタンを押してもらう

## 使用環境

Android バージョン8.0以上 推奨

## 使い方
まず、新規登録ボタンをタップします。
<p align="center">
<kbd><img src="https://user-images.githubusercontent.com/66397523/213898848-48efb577-17c9-4a11-b77a-26838c37a337.png" width="300"></kbd>
</p>

次に、カメラ起動ボタンをタップします。そうするとQRコード読み取り画面に移動します。
<p align="center">
<kbd><img src="https://user-images.githubusercontent.com/66397523/213900554-a668969b-e251-46fa-9e33-cc2410b85225.png" width="300"></kbd>
</p>

読み込み終わったら登録ボタンをタップします。
<p align="center">
<kbd><img src="https://user-images.githubusercontent.com/66397523/213900562-ba55931f-38fd-47bc-b20d-3432dc2c1ff2.png" width="300"></kbd>
</p>

登録した情報を見るには薬の情報ボタンをタップします。
<p align="center">
<kbd><img src="https://user-images.githubusercontent.com/66397523/213900576-03865360-436f-4c3b-941d-c6827183c768.png" width="300"></kbd>
</p>

通知の設定をするには通知設定ボタンをタップします。
<p align="center">
<kbd><img src="https://user-images.githubusercontent.com/66397523/213900583-28f8e76b-a720-49cb-a982-f783ca136517.png" width="300"></kbd>
</p>

次に、追加ボタンをタップします。
<p align="center">
<kbd><img src="https://user-images.githubusercontent.com/66397523/213900592-87857fb0-2132-4f0a-a55a-42ef30b926ae.png" width="300"></kbd>
</p>

そうすると朝、昼、夜の選択肢がでてくるので通知がほしい時間帯にチェックをつけます。
(初期設定だと、7時、12時、19時にせっていされています。)
チェックをつけたら次にボタンをタップします。
<p align="center">
<kbd><img src="https://user-images.githubusercontent.com/66397523/213900599-1d06b6c5-0dd1-4cac-a80d-abb4a678a9be.png" width="300"></kbd>
</p>

画面が移動したら登録した薬の名前をタップし、登録ボタンをタップします。
<p align="center">
<kbd><img src="https://user-images.githubusercontent.com/66397523/213900608-2408126f-935f-4261-9d93-2dd56b08cbb8.png" width="300"></kbd>
</p>

これで通知の設定は完了です。
<p align="center">
<kbd><img src="https://user-images.githubusercontent.com/66397523/213900618-d854d591-8028-47cc-b7b6-aa06f2d1674d.png" width="300"></kbd>
</p>

通知がきたら通知をタップすることでアプリが起動します。
<p align="center">
<kbd><img src="https://user-images.githubusercontent.com/66397523/213900632-83d77327-9972-405c-9272-173533be4144.png" width="300"></kbd>
</p>

設定した通知をタップすることで確認画面に移動します。
<p align="center">
<kbd><img src="https://user-images.githubusercontent.com/66397523/213900716-3160cb0c-5ade-4930-8d26-d1aed0d65ffc.png" width="300"></kbd>
</p>

最後に薬を飲んだボタンをタップします。
<p align="center">
<kbd><img src="https://user-images.githubusercontent.com/66397523/213900642-e5121afa-0982-4376-bac1-968206d3b7f5.png" width="300"></kbd>
</p>

## インストール方法

このリポジトリをクローンしていただき、インストールします。

~~~
$ git clone https://github.com/s20003/QRCodeRealmTest2
~~~

## 製作者

- [s20003](https://github.com/s20003)
- [s20013](https://github.com/s20013)

## FAQ

*Q.* QRコードはどうやってとればいいですか？

*A.* 薬局でQRコードを欲しいと言ってくれればもらえます

*Q.* どうやって時間を設定すればいいですか？

*A.* アラームのボタンから自由に設定できるます

## 今後の計画

 - 利用者がお年寄りの場合、薬の通知があった際に家族にも同じ
通知が来るようにする

- Google Play Store でリリースする

[main](https://github.com/s20003/QRCodeRealmTest2/tree/master/app/src/main/java/jp/ac/it_college/std/s20003/qrcoderealmtest2)
