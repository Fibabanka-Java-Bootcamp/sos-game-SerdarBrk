package org.kodluyoruz;


import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class SOSOyun {
    private char[][] sosOyunTablosu=null;
    int[] indexDizi=null;
    private boolean siraBilgisayar=false;
    private boolean siraKisi=false;
    private char kisiHarf;
    private char bilgisayarHarf;
    private int kisiPuan=0;
    private int bilgisayarPuan=0;
    private int n;
    private String cevap;

    public void oyunuBaslat(){
        Scanner scanner=new Scanner(System.in);
        do{
            do{
                nDegerAl();
            }while(!oyunBaslasinMi(n));
            matrisleriOlustur(n);
            rastgeleHarfVeSira();
            do{
                if(siraKisi){
                    while(!indexAl());
                    harfEkle(indexDizi[0],indexDizi[1],kisiHarf);
                    if(puanKontrol(indexDizi[0],indexDizi[1],kisiHarf)){siraKisi=true; siraBilgisayar=false;}
                    else{siraKisi=false; siraBilgisayar=true;}
                }else{
                    int satir,sutun;
                    do {
                        satir = (int) (Math.random() * (n));
                        sutun = (int) (Math.random() * (n));
                    }while(indexDoluMu(satir,sutun));
                    harfEkle(satir,sutun,bilgisayarHarf);
                    if(puanKontrol(satir,sutun,bilgisayarHarf)){siraKisi=false; siraBilgisayar=true;}
                    else{siraKisi=true; siraBilgisayar=false;}
                }
                matrisYazdir();
            }while(!matrisDolduMu());

            do{
                System.out.println("Devam etmek ister misiniz? [EVET/HAYIR]");
                cevap=scanner.nextLine();
            }while(tekrarSorulsunMu(cevap));
        }while(oyunaDevamMi(cevap));
    }
    private void nDegerAl(){

        Scanner scanner=new Scanner(System.in);
        System.out.println("Lütfen NxN matris için [3,7] aralığında bir N tamsayı değeri giriniz.");
        try{
            n=scanner.nextInt();
        }catch (Exception e){
            System.out.println("IllegalArgumentException: Lütfen [3,7] aralığında bir tamsayı giriniz.");
            n=-1;
        }
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
    }

    private void matrisleriOlustur(int satirSutun){
        sosOyunTablosu=new char[satirSutun][satirSutun];
    }

    private boolean oyunBaslasinMi(int satirSutun){
        if(satirSutun <3 || satirSutun >7) {
            System.out.println("Lütfen aralık içerisinde bir değer giriniz..");
            return false;
        }
        return true;
    }

    private void rastgeleHarfVeSira(){
        Random rnd = new Random();
        int harfNo=rnd.nextInt() % 2;
        if(harfNo == 0){
            siraBilgisayar=true;
        }else{
            siraKisi=true;
        }
        int siraNo=rnd.nextInt()%2;
        if(siraNo == 1){
            kisiHarf='o';
            bilgisayarHarf='s';
        }else{
            kisiHarf='s';
            bilgisayarHarf='o';
        }
        System.out.println((siraKisi ? "İlk siz başlıyorsunuz.\n": "İlk bilgisayar başlıyor.\n")+"Sizin harfiniz:"+kisiHarf+
                "\nBilgisayarın harfi: "+bilgisayarHarf);
    }

    private boolean indexAl(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("Lütfen satir ve sutun numarasını 'satir sutun' şeklinde giriniz. "+
                "Örneğin: 0 0");
        try{
          indexDizi= Arrays.asList(scanner.nextLine().split(" ")).stream().mapToInt(Integer::parseInt).toArray();
          if(indexDizi.length > 2){
              System.out.println("Lütfen 2 den fazla tamsayı yazmayınız.");
              return false;
          }else if(indexDizi[0]<0 || indexDizi[1]<0 || indexDizi[0] >n-1 || indexDizi[1] > n-1){
              System.out.println("Lütfen [0,"+(n-1)+"] aralığında tamsayı girişi yapınız.");
              return false;
          }else{
              if(indexDoluMu(indexDizi[0],indexDizi[1])){
                  System.out.println("Girmiş olduğunuz indexe daha önceden harf yazılmış..");
                  return false;
              }
              else
                  return true;
          }
        }catch (IllegalArgumentException e) {
            System.out.println("Lütfen tamsayıdan başka değer girmeyiniz...");
            return false;
        }catch(Exception e){
            System.out.println("Lütfen 'satır sütun' şeklinde giriş yapınız... ");
            return false;
        }
    }

    private boolean indexDoluMu(int satir,int sutun){
        if(sosOyunTablosu[satir][sutun] == '\u0000')
            return false;
        else
            return true;
    }

    private void harfEkle(int satir,int sutun,char harf){
        sosOyunTablosu[satir][sutun]=harf;
    }

    private boolean puanKontrol(int satir,int sutun,char harf){
        boolean sosOlduMu=false;
        if(harf=='o'){
            if(satir-1 >= 0 && satir+1 < n){
                if((String.valueOf(sosOyunTablosu[satir-1][sutun])+String.valueOf(sosOyunTablosu[satir][sutun])+
                        String.valueOf(sosOyunTablosu[satir+1][sutun])).equals("sos")){
                    if(siraKisi){
                        kisiPuan++;
                        sosOlduMu=true;
                    }
                    else {
                        bilgisayarPuan++;
                        sosOlduMu=true;
                    }
                }
            }

            if(sutun-1 >=0 && sutun+1 <n){
                if((String.valueOf(sosOyunTablosu[satir][sutun-1])+String.valueOf(sosOyunTablosu[satir][sutun])+
                        String.valueOf(sosOyunTablosu[satir][sutun+1])).equals("sos")){
                    if(siraKisi){
                        kisiPuan++;
                        sosOlduMu=true;
                    }
                    else {
                        bilgisayarPuan++;
                        sosOlduMu=true;
                    }
                }
            }
            if((sutun-1>=0 && satir-1 >=0) && (sutun+1 < n && satir+1 < n)){
                if((String.valueOf(sosOyunTablosu[satir-1][sutun-1])+String.valueOf(sosOyunTablosu[satir][sutun])+
                        String.valueOf(sosOyunTablosu[satir+1][sutun+1])).equals("sos")){
                    if(siraKisi){
                        kisiPuan++;
                        sosOlduMu=true;
                    }
                    else {
                        bilgisayarPuan++;
                        sosOlduMu=true;
                    }
                }
            }

            if((satir-1 >= 0 && sutun + 1 < n) && (sutun - 1 >=0 && satir + 1 < n)){
                if((String.valueOf(sosOyunTablosu[satir-1][sutun+1])+String.valueOf(sosOyunTablosu[satir][sutun])+
                        String.valueOf(sosOyunTablosu[satir+1][sutun-1])).equals("sos")){
                    if(siraKisi){
                        kisiPuan++;
                        sosOlduMu=true;
                    }
                    else {
                        bilgisayarPuan++;
                        sosOlduMu=true;
                    }
                }
            }
        }else{
            if(satir-2 >=0){
                if((String.valueOf(sosOyunTablosu[satir-2][sutun])+String.valueOf(sosOyunTablosu[satir-1][sutun])+
                        String.valueOf(sosOyunTablosu[satir][sutun])).equals("sos")){
                    if(siraKisi){
                        kisiPuan++;
                        sosOlduMu=true;
                    }
                    else {
                        bilgisayarPuan++;
                        sosOlduMu=true;
                    }
                }
                if(sutun-2>=0){
                    if((String.valueOf(sosOyunTablosu[satir-2][sutun-2])+String.valueOf(sosOyunTablosu[satir-1][sutun-1])+
                            String.valueOf(sosOyunTablosu[satir][sutun])).equals("sos")){
                        if(siraKisi){
                            kisiPuan++;
                            sosOlduMu=true;
                        }
                        else {
                            bilgisayarPuan++;
                            sosOlduMu=true;
                        }
                    }
                }
            }
            if(satir+2 < n){
                if((String.valueOf(sosOyunTablosu[satir][sutun])+String.valueOf(sosOyunTablosu[satir+1][sutun])+
                        String.valueOf(sosOyunTablosu[satir+2][sutun])).equals("sos")){
                    if(siraKisi){
                        kisiPuan++;
                        sosOlduMu=true;
                    }
                    else {
                        bilgisayarPuan++;
                        sosOlduMu=true;
                    }
                }
                if(sutun + 2 <n){
                    if((String.valueOf(sosOyunTablosu[satir][sutun])+String.valueOf(sosOyunTablosu[satir+1][sutun+1])+
                            String.valueOf(sosOyunTablosu[satir+2][sutun+2])).equals("sos")){
                        if(siraKisi){
                            kisiPuan++;
                            sosOlduMu=true;
                        }
                        else {
                            bilgisayarPuan++;
                            sosOlduMu=true;
                        }
                    }
                }
            }
            if(sutun-2 >= 0){
                if((String.valueOf(sosOyunTablosu[satir][sutun-2])+String.valueOf(sosOyunTablosu[satir][sutun-1])+
                        String.valueOf(sosOyunTablosu[satir][sutun])).equals("sos")){
                    if(siraKisi){
                        kisiPuan++;
                        sosOlduMu=true;
                    }
                    else {
                        bilgisayarPuan++;
                        sosOlduMu=true;
                    }
                }
                if(satir+2 <n){
                    if((String.valueOf(sosOyunTablosu[satir][sutun])+String.valueOf(sosOyunTablosu[satir+1][sutun-1])+
                            String.valueOf(sosOyunTablosu[satir+2][sutun-2])).equals("sos")){
                        if(siraKisi){
                            kisiPuan++;
                            sosOlduMu=true;
                        }
                        else {
                            bilgisayarPuan++;
                            sosOlduMu=true;
                        }
                    }
                }
            }
            if(sutun+2 <n){
                if((String.valueOf(sosOyunTablosu[satir][sutun])+String.valueOf(sosOyunTablosu[satir][sutun+1])+
                        String.valueOf(sosOyunTablosu[satir][sutun+2])).equals("sos")){
                    if(siraKisi){
                        kisiPuan++;
                        sosOlduMu=true;
                    }
                    else {
                        bilgisayarPuan++;
                        sosOlduMu=true;
                    }
                }
                if(satir-2 >= 0){
                    if((String.valueOf(sosOyunTablosu[satir][sutun])+String.valueOf(sosOyunTablosu[satir-1][sutun+1])+
                            String.valueOf(sosOyunTablosu[satir-2][sutun+2])).equals("sos")){
                        if(siraKisi){
                            kisiPuan++;
                            sosOlduMu=true;
                        }
                        else {
                            bilgisayarPuan++;
                            sosOlduMu=true;
                        }
                    }
                }
            }
        }
        return sosOlduMu;
    }

    private boolean matrisDolduMu(){
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(sosOyunTablosu[i][j] == '\u0000')
                    return false;
            }
        }
        return true;
    }

    private boolean tekrarSorulsunMu(String cevap){
        if(cevap.toUpperCase().equals("EVET")){
            return false;
        }else if(cevap.toUpperCase().equals("HAYIR")){
            return false;
        }else{
            System.out.println("Lütfen EVET ya da HAYIR giriniz...");
            return true;
        }
    }

    private boolean oyunaDevamMi(String cevap) {
        if(cevap.toUpperCase().equals("EVET"))
            return true;
        else
            return false;
    }

    private void matrisYazdir(){

        String s="";
        for(int i=0;i<n;i++){
            s+="-";
        }
        System.out.println("|-"+s+"-|");
        for(char[] cA: sosOyunTablosu){
            for(char c:cA){
                if(c != '\u0000')
                    System.out.print("|"+c);
                else
                    System.out.print("|"+Character.toString ((char) 186));

            }
            System.out.println("|");
        }
        System.out.println("|-"+s+"-|");
        System.out.println("|Puanınız          :"+kisiPuan+"|");
        System.out.println("|Bilgisayar Puanını:"+bilgisayarPuan+"|");
    }

}
