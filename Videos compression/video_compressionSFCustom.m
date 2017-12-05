%creo immagini compressed e verifico plot

close all;
clear all;

vet_files=dir('RBSvideo/*.png');
nframe = 220; 
SF=[1:-.1:0.1];

err = zeros(size(SF));
videosize=err;
Tframe = 24e-3; 
for i=1:nframe;

    fullframename = strcat('RBSvideo/',vet_files(i).name); 
    I=uint8(imread(fullframename));
    
    disp(['Compressing frame #',num2str(i)]);
    for iq=1:length(SF),
        q=SF(iq);
        
        dirname = ['RBSvideoC',num2str(q)];
        if ~exist(dirname,'dir'),
            mkdir('.',dirname);
        end
        compframename = [dirname,'/frame',num2str(i),'.jpg'];
        IR=imresize(rgb2ycbcr(I),q);
        
        %size of the image
        [N,M,ch]=size(IR);

        %generate Bayer mask matrix
        %We assume bayer mask has structure
        %G R
        %B G
        Is=zeros(N,M);
        Is(1:2:N,1:2:M)=IR(1:2:N,1:2:M,2);   %G
        Is(2:2:N,1:2:M)=IR(2:2:N,1:2:M,3);   %B
        Is(1:2:N,2:2:M)=IR(1:2:N,2:2:M,1);   %R
        Is(2:2:N,2:2:M)=IR(2:2:N,2:2:M,2);   %G

        if mod(size(Is),2) == 1
            Is(end+1,:)=Is(end,:);
            Is(:,end+1)=Is(:,end);
        end
        
        
        imwrite(uint8(Is),compframename);
        z=dir(compframename);
        videosize(iq) = videosize(iq)+z.bytes; 
        IC = imread(compframename);
        IH = horizontal_debayer1(IC);
        IR = ycbcr2rgb(imresize(IH,[360 640]));
        e= (IR-I).^2;
        err(iq) = err(iq) + sum(e(:));
    end
    clear Is;
    clear IR;
    clear IH;
    clear IC;
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