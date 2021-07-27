# GravitySurvival

重力が３０秒ごとに変わる重力MODのアドオン  
前提に[Up And Down And All Around](https://www.curseforge.com/minecraft/mc-mods/up-and-down-and-all-around)が必要です

1.12.2 Forge

# 仕様

* gravitysurvival
             * start 回転までの時間<1～>(省略可) バラバラ回転か[true/false](省略可)  
             * stop 停止  
             * change 変更  
                 * speed  回転までの時間（秒）  
                 * uniform  全員バラバラに回転するか固定かどうか(true/false)  
                 * forcedrotation 強制回転（クリエーティブやスペクターでも回転) デフォルトではfalse  

開始コマンド例  
/gravitysurvival start 19 true

変更コマンド例  
/gravitysurvival change speed 364 (364秒ごとに回転)   
/gravitysurvival change uniform true (バラバラ回転)  
/gravitysurvival change forcedrotation false (強制回転無効化)  



