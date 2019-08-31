close all;
clear all;

decompressFrames = true;
%decompressFrames has to be true to viewPlot
viewPlot = true;
%decompressFrames has to be true to writeDecompressedImages 
writeDecompressedImages = false;
%writeDecompressedImages before writeDecompressedVideos 
writeDecompressedVideos = false;

vet_files=dir('RBSvideo/*.png');
nframe = 220; 
SF=[1:-.1:0.1];

err = zeros(size(SF));
videosize=err;
Tframe = 24e-3; 
if decompressFrames,
    for i=1:nframe;
        if viewPlot,
            fullframename = strcat('RBSvideo/',vet_files(i).name); 
            I=uint8(imread(fullframename));
        end

        disp(['Decompressing frame #',num2str(i)]);
        for iq=1:length(SF),
            q=SF(iq);

            dirname = ['RBSvideoC',num2str(q)];
            if ~exist(dirname,'dir'),
                mkdir('.',dirname);
            end

            compframename = [dirname,'/frame',num2str(i),'.jpg'];
            z=dir(compframename);
            videosize(iq) = videosize(iq)+z.bytes; 
            IC = imread(compframename);
            IH = horizontal_debayer1(IC);
            IR = ycbcr2rgb(imresize(IH,[360 640]));

            if writeDecompressedImages,
                dirname = ['RBSvideoD',num2str(q)];
                if ~exist(dirname,'dir'),
                    mkdir('.',dirname);
                end
                decompframename = [dirname,'/frame',num2str(i),'.jpg'];
                imwrite(uint8(IR),decompframename);
            end

            if viewPlot,
                e= (IR-I).^2;
                err(iq) = err(iq) + sum(e(:));
            end;
        end
        clear Is;
        clear IR;
        clear IH;
        clear IC;
    end
end
%%
if writeDecompressedVideos,
    for iq=1:length(SF),
        q=SF(iq);
        disp(strcat('Making video',num2str(q)));
        dirname = ['RBSvideoD',num2str(q)];
        for i=1:nframe,
            decompframename = [dirname,'/frame',num2str(i),'.jpg'];
            M(i)=im2frame(imread(decompframename));
        end
        v = VideoWriter(strcat('video',num2str(iq),'.avi'),'Uncompressed AVI');
        v.FrameRate = 24;
        open(v);
        writeVideo(v,M);
        close(v);
    end
end

if viewPlot,
    err = err/nframe/numel(I);

    PSNR = 10*log10(255^2./double(err));
    rate = videosize*8/1e6/nframe/Tframe; 
    figure(1)
    clf
    plot(rate,PSNR);
    xlabel('rate [Mbit/s]')
    ylabel('PSNR [dB]')
end