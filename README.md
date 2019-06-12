
# OpenCVAAMaker
AAを画像から作ります

## version
OpenCV3で書いているのでOpenCV3を導入してから動かしてください（開発バージョンは3.4.4です）

## 設定
Eclipseで動かしてください（じぶんで設定ができるならEclipseでなくても動かせる）
System.load()のところを自分のOpenCVの　opencv_java(バージョン).dll　のディレクトリ（.dllまで含める）に設定する


## how to use
Sample.jpgを動かすプログラムと同じディレクトリに入れて使う。（勿論ソースをいじれば読みこむファイルを変えられる）
cluster_sizeを適当に設定する。（画像分割したときの１辺のピクセル数がcluster_size）
動かすと、res.txtに結果が出力される。

## 動かした例

![/img/show.png]