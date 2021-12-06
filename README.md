# 15 Puzzle

This repository is a final project (Java GUI) from Object-Oriented Programming Class, Teknik Informatika Universitas Padjadjaran. 

[Challenge Guidelines](challenge-guideline.md)

**Proyek untuk mengimplementasikan game 15 Puzzle yang merupakan puzzle dengan size tile(petak) 4x4 dimana mengharuskan pemain untuk menyusun puzzle menjadi berurutan secara horizontal ke arah kanan dari baris paling atas dengan cara mengklik petak yang berisi angka sehingga bisa berpindah ke petak yang kosong sampai puzzle terselesaikan**

## Credits
| NPM           | Name        |
| ------------- |-------------|
| 140810200021  | Rifqi Akmal Fauzi    |
| 140810200031  | Ahmad Yahya Salim	|
| 140810200055  | Wafa Tsabita |

## Change log
- **[Sprint Planning](changelog/sprint-planning.md) - (22/11/2021)** 
   - diskusi rencana awal backlog

- **[Sprint 1](changelog/sprint-1.md) - (16/11/2021 - 22/11/2021)** 
   - Inisialisasi OOP dan JFX
   - Membuat kerangka class puzzle

- **[Sprint 2](changelog/sprint-2.md) - (23/11/2021 - 29/11/2021)** 
   - Membuat Puzzle untuk 16 tiles
   - Menambahkan pergerakan dengan mouse listener
   
- **[Sprint 3](changelog/sprint-3.md) - (30/11/2021 - 6/12/2021)** 
   - Short changes 1
   - Short changes 2

## Running The App

- **Menggunakan Gradle**
1. Sesuaikan directori pada CMD atau Terminal pada folder [fifteenPuzzleGradle](fifteenPuzzleGradle)

2. Dalam direktori tersebut jalankan :
   - `gradlew run`  untuk ukuran default 4 x 4 tiles
   - `gradlew run --args="n"` untuk ukuran puzzle n x n

## Classes Used

![UML](https://github.com/praktikum-tiunpad-2021/oop-final-kelompok-a-12/blob/master/UML.jpg)


## Notable Assumption and Design App Details

- Aplikasi puzzle dijalankan secara default dengan ukuran 4 x 4.
- Customisasi ukurang dapat dilakukan dengan passing pada args saat menjalankan aplikasi *dengan catatan dibatasi ukuran terkecil n = 3 dan ukuran maksimal yang diharapkan tidak melebihi resolusi layar agar puzzle masuk akal untuk dimainkan*.
- Secara default tampilan telah teracak dan tersedia tombol `reset puzzle` untuk mengacak ulang puzzle.
- Pengacakan puzzle menggunakan penghitungan inversi agar hasil acak mungkin diselesaikan dengan detail sebagai berikut :

	*inversi adalah ketika suatu angka pada posisi tertentu didahului angka yang lebih besar*
	*contoh : 2 1 3 4 , terdapat 1 buah inversi pada (2 , 1)*
	- Apabila jumlah sisi atau `n = ganjil` maka inversi harus berjumlah genap.
	- Apabila jumlah sisi atau `n = genap` :
 		- ketika letak tiles kosong dari baris paling bawah adalah ganjil, total inversi harus genap.
 		- ketika letak tiles kosong dari baris paling bawah adalah genap, total inversi harus ganjil.
- Tiap klik mouse dari user akan dicek koordinatnya relatif dari tiles kosong agar bisa melakukan sebuah langkah pergerakan.
- Setiap 1 langkah akan dicek apakah puzzle telah terurut dan jika sudah akan diberi tampilan permainan selesai.
- Akhir permainan akan diberi tampilan jumlah langkah yang dilakukan user dalam menyelesaikan permainan.