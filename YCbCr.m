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

%convert in YUV
R = I(:,:,1);
G = I(:,:,2);
B = I(:,:,3);
Y = 0.299 * R + 0.587 * G + 0.114 * B; 
U = -0.14713 * R - 0.28886 * G + 0.436 * B; 
V = 0.615 * R - 0.51499 * G - 0.10001 * B; 

%convert to YCbCr
I = rgb2ycbcr(I);

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

%interpolazione orizzontale
Ih=horizontal_debayer1(Is);

%calcola le differenze su ciascuna componente di colore
dRh=double(I(:,:,1)-Ih(:,:,1));
dGh=double(I(:,:,2)-Ih(:,:,2));
dBh=double(I(:,:,3)-Ih(:,:,3));

%visualizza le differenze
%interpolazione orizzontale
figure(1)
subplot(2,2,1);
imshow(ycbcr2rgb(I));
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