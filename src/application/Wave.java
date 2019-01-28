public class Wave{
    public static void main (String[] args) {
        
        double a = 0.0;         // 積分区間の左端
        double b = 5.0;         // 積分区間の右端
        int N = 50;             // 分割した区間の数

        double h = (b-a)/N;        // 区間の幅 0.1 = (5-0)/50

        double sum = 0;   // 面積の途中計算結果を入れるための変数

        for (int i=0; i<N; i++) { // 以下を N 回繰り返す
            
           // i番目の台形の左下X値
        　  double x = A + i * delta;
        
           // i 番目の地点までの積分値を出力
            printf("%f\t%f\n", x, value);
        
            sum = sum + (i*h+2*Math.sin(i*h)+(i+1)*h+2*Math.sin((i+1)*h))*h/2;

        }  // for の終わり

        System.out.println("近似値 = " + sum);  // 結果の出力 

        System.out.println("本当の値 = " + 
               ((5.0*5.0/2 - 2*Math.cos(5.0))-(0.0*0.0/2- 2*Math.cos(0.0))));

          　// 最終的な積分結果(区間全体の面積)をコンソールに出力
             printf("%f\t%f\n", B, value);

    } // main メソッドの終わり
} // daikei クラスの終わり

