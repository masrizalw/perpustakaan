# Perpustakaan Sederhana

Perpustakaan sederhana ini membolehkan satu pengguna meminjam maksimal satu buku. Buku-buku memiliki batas stok. Ada banyak role yang bisa diterapkan, namun pada umumnya hanya dua role yang bisa dimaksimalkan penggunaanya. Role sebagai administrator ataupun role sebagai pengguna umum.

## Appendix

Aplikasi ini hanya menyediakan backend saja. untuk frontend akan di upload terpisah dengan nama Perpustakaan FE.


## Authors

- [@masrizalw](https://www.github.com/masrizalw)


## Installation

Clone the project

```bash
  git clone https://github.com/masrizalw/perpustakaan.git
```

Go to the project directory

```bash
  cd perpustakaan
```

Compile dengan IDE seperti eclipse, intellij, dll. atau melalui langkah manual berikut:

- Download dan install maven
  - [tutorial install](https://www.baeldung.com/install-maven-on-windows-linux-mac)
  - [link download](https://maven.apache.org/download.cgi)
    
- Pada direktory root **/Perpustakaan**, jalankan perintah berikut
```bash
  mvn compile
  mvn package
```

- File app-0.0.1-SNAPSHOT.jar akan tergenerate di direktory /Perpustakaan/target
```bash
  cd target
```

- Jalankan aplikasi
```bash
  java -jar app-0.0.1-SNAPSHOT.jar
```

## Usage/Examples

1. Gunakan postman atau akses ke [localhost](http://localhost:8080/swagger)
2. Untuk membuka h2 database akses ke **/h2-console**, username *sa* password *root*

## Tech Stack

**Language:** Java
**Framework:** Springboot
**Database:** H2

