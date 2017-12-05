%Laboratorio di Telecomunicazioni
%Esperienza di laboratorio numero 8
%Compressione del video in termini di qualit? con JPEG
%
%Lo script carica una sequenza di immagini che formano un video e le
%comprime usando la codifica JPEG con qualit? diversa (SF). Per ogni valore
%di qualit? xyz, si crea una nuova directory, dal nome RBSvideoQxyz, in cui
%vengono salvati i frame compressi in formato jpeg. 
%
% Lo script calcola anche il bitrate e il PSNR per ogni video compresso e
% mostra il grafico PSNR vs Rate risultante. 

close all;
clear all;

vet_files=dir('RBSvideo/*.png');
nframe = 220; 
SF=[1:-0.1:0.1];

err = zeros(size(SF));
videosize=err;
Tframe = 24e-3; 
for i=1:nframe;

    fullframename = strcat('RBSvideo/',vet_files(i).name); 
    I=imread(fullframename);
    sizefile =0; 
    disp(['Compressing frame #',num2str(i)]);
    for iq=1:length(SF),
        q=SF(iq);
        
        dirname = ['RBSvideoS',num2str(q)];
        if ~exist(dirname,'dir'),
            mkdir('.',dirname);
        end
        compframename = [dirname,'/frame',num2str(i),'.jpg'];
        IR=imresize(I,q);
        imwrite(IR,compframename,'JPEG')
        z=dir(compframename);
        videosize(iq) = videosize(iq)+z.bytes; 
        IC = imread(compframename);
        IR=imresize(IC,[360 640]);
        e= (IR-I).^2;
        err(iq) = err(iq) + sum(e(:));
%     imshow(I);  %visualizzo
    end
end
%%
err = err/nframe/numel(I);

PSNR = 10*log10(255^2./double(err));
rate = videosize*8/1e6/nframe/Tframe; 
figure(1)
clf
plot(rate,PSNR);
xlabel('rate [Mbit/s]')
ylabel('PSNR [dB]')