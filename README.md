## Proje Bilgileri
- Java platformuyla geliştirilmiş `*.csv` dosyasından İYS API'ye çoklu izin ekleme örnek projesidir.
- Windows işletim sisteminde geliştirilmiştir.

## Derleme
Projeyi build etmek için aşağıdaki adımları uygulayın.

1. Bilgisayarınıza Java 1.8 jdk yüklü olduğundan emin olun. Yüklü değilse [buraya](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html) tıklayarak indirebilirsiniz.
2. [Maven](https://maven.apache.org/)'ın 3.6.3 sürümünü indrip windows için environment variable ayarlarını yapın.
3. Proje klasörü içerisinde aşağıdaki komutu çalıştırın  
  - ```mvn -U clean install -DskipTest```
4. Proje başarılı bir şekilde derlendikten sonra target klasörü altına `client-1.0.0-RELEASE.jar` dosyası oluşturulacaktır.
5. `client-1.0.0-RELEASE.jar` dosyasını herhangi bir klasöre kopyalayın. Aynı klasör içerisine bilgisayarınıza klonladığınız `csv` klasörünü de kopyalayarak çalışır hale getirebilirsiniz.

## Konfigürasyonlar

> `csv/config.yml` dosyası içeriği, kullanıcıya özeldir ve hesap bilgilerinizle güncellenmelidir.

- `iysCode`, `brandCode`, `user` ve `password` bilgilerini ilgili yerlere giriniz.
- csv klasörü altında bulunan data.csv dosyasında bulunan örnek izinlerdeki gibi izinlerinizi satır satır ekleyiniz.
- Konfigürasyonlar hazır olduktan sonra `start.cmd` dosyasını sağ tıklayıp yönetici modunda çalıştırın.
- Eğer csv dosyasında hata varsa, açılan pencereden hangi satırda nasıl bir hata olduğunu öğrenip gerekli düzenlemeleri yapın.
- Proje başarılı bir şekilde çalıştıktan sonra csv klasörü altında `success.csv`, `error.csv` ve `resume.csv` ismindeki dosyaları oluşturacaktır.
- `success.csv` dosyası, başarılı bir şekilde İYS API'ye gönderilen izinlerin kaydedildiği dosyadır. requestId ya da subRequestId parametrelerini kullanarak o iznin nihai sonucunu öğrenebilirsiniz.
- `error.csv` dosyası, gönderilemeyen ve hata alan kayıtları listeler. Hangi satırda, hangi izin nesnesinin hangi hatayı aldığını görebilirsiniz.
- `resume.csv` dosyası gönderilemeyen ve hata alan kayıtların, tekrar gönderilmek üzere eklendiği dosyadır. error.csv dosyasında hangi satırda hata varsa, resume.csv dosyasında ilgili satırdaki izninde bulunan hata/hatalar düzeltilmelidir.
- Düzenleme yapıp kaydettikten sonra `resume.csv` dosyasını kapatın.
- `resume.cmd` dosyasına sağ tıklayarak yönetici modunda çalıştırın.
- Devam etme işlemi sonrası `resume_success.csv` ve `resume_error.csv` dosyaları oluşacaktır.
- `resume_error.csv` dosyasından hataları bulup, `resume.csv` dosyasında ilgili satırı düzenleyerek `resume.cmd` dosyasını çalıştırın.
- Tüm kayıtların eklenmesi işlemi tamamlanana kadar gerekli düzenlemeleri resume.csv ile yaparak `resume.cmd` dosyasını çalıştırın.

## Nasıl kullanılır?
- Projeyi aşağıdaki komutla bilgisayarınıza herhangi bir klasöre klonlayın.
```
git clone https://github.com/iysorgtr/csv-ile-toplu-izin-gonderme.git
```
- Gerekli diğer dosyalar proje klasörü altında bulunmaktadır.
- `client-1.0.0-RELEASE.jar` çoklu izinleri gönderen java client örnek uygulamasıdır.
- `start.cmd` dosyası, gerekli konfigürasyonlar yapıldıktan sonra izinleri eklemek için çalıştırılacak dosyadır.  
- `resume.cmd` dosyası, izinler yazıldıktan sonra alınan hatalar varsa çalıştırılacak dosyadır.  
- csv klasörü altında yer alan `data.csv` dosyası, çoklu halde eklenecek izinlerin bulunduğu dosyadır.  
- csv klasörü altında yer alan `config.yml` dosyası, servis için konfigürasyonların yapıldığı, `iysCode`, `brandCode`, kullanıcı adı, şifre gibi bilgilerin kaydedileceği config dosyasıdır.  
- Klonladığınız klasöre girerek proje klasörüne ulaşın.
- csv klasörü altında bulunan `config.yml` dosyasını, herhangi bir editör yardımıyla düzenleyin.  

İYS API hakkında daha detaylı bilgi almak için [Geliştirici Merkezi](https://dev.iys.org.tr/)'ni ziyaret edebilirsiniz.

