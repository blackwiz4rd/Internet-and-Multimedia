%Laboratorio di Internet e Multimedia
%Esperienza di laboratorio Immagini n. 1
%Tecniche di demosaicing: applicazioni in image tampering
%8/11/2017
%
%docente: Simone Milani (simone.milani@dei.unipd.it)
%
%Localize the tampering

clear all;
close all;

%carica l'immagine
I=uint8(imread('frame0001.png'));


%calcola le dimensioni
[N,M,ch]=size(I);

%genera una Bayer mask matrix
%si assume che la bayer mask abbia struttura 
%G R
%B G
Is=zeros(N,M);
Is(1:2:N,1:2:M)=I(1:2:N,1:2:M,2);
Is(2:2:N,1:2:M)=I(2:2:N,1:2:M,3);
Is(1:2:N,2:2:M)=I(1:2:N,2:2:M,1);
Is(2:2:N,2:2:M)=I(2:2:N,2:2:M,2);

%interpolazione verticale
Iv=vertical_debayer(Is);

%interpolazione orizzontale
Ih=horizontal_debayer(Is);

%calcola le differenze su ciascuna componente di colore
dRh=double(I(:,:,1)-Ih(:,:,1));
dGh=double(I(:,:,2)-Ih(:,:,2));
dBh=double(I(:,:,3)-Ih(:,:,3));
dRv=double(I(:,:,1)-Iv(:,:,1));
dGv=double(I(:,:,2)-Iv(:,:,2));
dBv=double(I(:,:,3)-Iv(:,:,3));

%visualizza le differenze
%interpolazione orizzontale
figure(1)
subplot(2,2,1);
imshow(I);
title('Immagine originale');
subplot(2,2,2);
imagesc(dRh);
title('dR con int. orizz.');
subplot(2,2,3);
imagesc(dGh);
title('dG con int. orizz.');
subplot(2,2,4);
imagesc(dBh);
title('dB con int. orizz.');


%visualizza le differenze
%interpolazione verticale
figure(2)
subplot(2,2,1);
imshow(I);
title('Immagine originale');
subplot(2,2,2);
imagesc(dRv);
title('dR con int. vert.');
subplot(2,2,3);
imagesc(dGv);
title('dG con int. vert.');
subplot(2,2,4);
imagesc(dBv);
title('dB con int. vert.');