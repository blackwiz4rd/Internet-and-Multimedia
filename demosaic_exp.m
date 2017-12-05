%Laboratorio di Internet e Multimedia
%Esperienza di laboratorio Immagini n. 1
%Tecniche di demosaicing
%8/11/2017
%
%docente: Simone Milani (simone.milani@dei.unipd.it)
%
%L'esperienza di laboratorio ? volta a misurare l'efficacia di tecniche di
%demosaicing differenti


clear all;
close all;

%read image from database (folder imdb)
if ispc
    I=imread('imdb\kodim01.png');
    addpath('demosaic1');
    addpath('demosaic2\nat')
else
    I=imread('frame0001.png');
    addpath('demosaic1');
    addpath('demosaic2/nat')
end;

%size of the image
[N,M,ch]=size(I);

%generate Bayer mask matrix
%We assume bayer mask has structure
%G R
%B G
Is=zeros(N,M);
Is(1:2:N,1:2:M)=I(1:2:N,1:2:M,2);   %G
Is(2:2:N,1:2:M)=I(2:2:N,1:2:M,3);   %B
Is(1:2:N,2:2:M)=I(1:2:N,2:2:M,1);   %R
Is(2:2:N,2:2:M)=I(2:2:N,2:2:M,2);   %G


%plor original image and sampled image
figure(1)
subplot(2,2,1);
imshow(I);
title('Ground truth');
subplot(2,2,2);
imshow(Is/255);
title('Sampled pixels');

%vertical interpolation
%Iv=vertical_debayer(Is(1:10,1:10));
Iv=vertical_debayer(Is);

%plor vertically-interpolated image
subplot(2,2,3);
imshow(Iv);
title('Vertically interpolated');
%compute distortion
mse=mean((I(:)-Iv(:)).^2);
psnrv=10*log10(255^2/mse);


%horizontal interpolation

%generate image Ih
%Ih=horizontal_debayer(Is);
Ih1=horizontal_debayer(Is);
Ih=horizontal_debayer1(Is);

%plot difference
dRh=double(Ih1(:,:,1)-Ih(:,:,1));
dGh=double(Ih1(:,:,2)-Ih(:,:,2));
dBh=double(Ih1(:,:,3)-Ih(:,:,3));
%visualizza le differenze
%interpolazione orizzontale
figure(3)
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


%plot horizontally-interpolated image
figure(1)
subplot(2,2,4);
imshow(Ih);
title('Horizontally interpolated');
%compute distortion
mse=mean((I(:)-Ih(:)).^2);
psnrh=10*log10(255^2/mse);


% In this part we exploit the CFA algoritms implemented in the repository
% 'demo_cfa'
%
% The package contains MATLAB implementation of the CFA demosaicing 
% algorithms
% 
% 1. B. K. Gunturk, Y. Altunbasak and R. M. Mersereau, ``Color plane
% interpolation using alternating projections ,'' {\em IEEE Trans.
% on Image Proc.}, vol. 11, no. 9, pp. 997-1013, Sep. 2002.
%
% use command >> Id=DemoPocs(I);
%
% 
% 2. Wenmain Lu and Yap-peng Tan, ``Color filter array demosaicing: new
% method and performance measures,'' {\em IEEE Trans. on Image
% Proc.}, vol. 12, no. 10, pp. 1194-1210, Oct. 2003.
%
% use command >> Id=CFA_lu(I);
% 
% 3. LDI-NAT algorithm algorithm
%
% use command >> Id=nat_cdm(I);
%
% 4. LDI-NLM algorithm algorithm
%
% use command >> Id=nlm_cdm(I);
%

%algorithm adaptive
Id=DemoPocs(I);

%plot result from   algorithm
figure(2);
imshow(Id);
mse=mean((I(:)-Id(:)).^2);
psnrd=10*log10(255^2/mse);


disp([psnrv psnrh psnrd]);