function[I]=horizontal_debayer(Is)

[N,M]=size(Is);

%immagine interpolata
Ih=zeros(N,M,3);
%la bayer mask ?
%G R
%B G

%%
%%creo la componente G

%copio i valori di G disponibili
%righe dispari
for y=1:2:N
    for x=1:2:M
        Ih(y,x,2)=Is(y,x);
    end;
end;

%righe pari
for y=2:2:N
    for x=2:2:M
        Ih(y,x,2)=Is(y,x);
    end;
end;

%(pi? velocemente si poteva fare)
%Ih(1:2:N,1:2:M,2)=Is(1:2:N,1:2:M);
%Ih(2:2:N,2:2:M,2)=Is(2:2:N,2:2:M);

%interpolo G
%righe dispari
for y=1:2:N
    for x=2:2:M-1
        Ih(y,x,2)=(Ih(y,x-1,2)+Ih(y,x+1,2))/2;
    end;
    Ih(y,M,2)=Ih(y,M-1,2);  %l'ultimo valore lo copio
end;

%pi? velocemente
%Ih(1:2:N,2:2:M-1,2)=(Ih(1:2:N,1:2:M-2,2)+Ih(1:2:N,3:2:M,2))/2;
%Ih(1:2:N,M,2)=Is(1:2:N,M-1,2);


%righe pari
for y=2:2:N
    for x=3:2:M
        Ih(y,x,2)=(Ih(y,x-1,2)+Ih(y,x+1,2))/2;
    end;
    Ih(y,1,2)=Ih(y,2,2);  %il primo valore lo copio
end;

%pi? velocemente
%Ih(2:2:N,3:2:M,2)=(Ih(2:2:N,2:2:M-1,2)+Ih(2:2:N,4:2:M,2))/2;
%Ih(2:2:N,1,2)=Ih(2:2:N,2,2);

%%fine G
%%





%%
%%creo la componente R

%copio i valori di R disponibili
%righe dispari
for y=1:2:N
    for x=2:2:M
        Ih(y,x,1)=Is(y,x);
    end;
end;

%(pi? velocemente si poteva fare)
%Ih(1:2:N,2:2:M,1)=Is(1:2:N,2:2:M);

%interpolo R
%righe dispari
for y=1:2:N
    for x=3:2:M
        Ih(y,x,1)=(Ih(y,x-1,1)+Ih(y,x+1,1))/2;
    end;
    Ih(y,M,1)=Ih(y,2,1);  %il primo valore lo copio
end;

%pi? velocemente
%Ih(1:2:N,3:2:M,1)=(Ih(1:2:N,2:2:M-1,1)+Ih(1:2:N,4:2:M,1))/2;
%Ih(1:2:N,1,1)=Ih(1:2:N,2,1);

%le righe pari possono essere interpolate in toto
for y=2:2:N-1
    Ih(y,:,1)=(Ih(y-1,:,1)+Ih(y+1,:,1))/2;
end;
Ih(N,:,1)=Ih(N-1,:,1);

%pi? velocemente
%Ih(2:2:N-1,:,1)=(Ih(1:2:N-2,:,1)+Ih(3:2:N,:,1))/2;
%Ih(N,:,1)=Ih(N-1,:,1);

%%fine R
%%


%%
%%creo la componente B

%copio i valori di B disponibili
%righe pari
for y=2:2:N
    for x=1:2:M
        Ih(y,x,3)=Is(y,x);
    end;
end;

%(pi? velocemente si poteva fare)
%Ih(2:2:N,1:2:M,3)=Is(2:2:N,1:2:M);

%interpolo B
%righe pari
for y=2:2:N
    for x=2:2:M-1
        Ih(y,x,3)=(Ih(y,x-1,3)+Ih(y,x+1,3))/2;
    end;
    Ih(y,M,3)=Ih(y,M-1,3);  %l'ultimo valore lo copio
end;

%pi? velocemente
%Ih(2:2:N,2:2:M-1,3)=(Ih(2:2:N,1:2:M-2,3)+Ih(2:2:N,3:2:M,3))/2;
%Ih(2:2:N,M,3)=Ih(2:2:N,M-1);

%le righe dispari possono essere interpolate in toto
for y=3:2:N
    Ih(y,:,3)=(Ih(y-1,:,3)+Ih(y+1,:,3))/2;
end;
Ih(1,:,3)=Ih(2,:,3);

%pi? velocemente
%Ih(3:2:N,:,3)=(Ih(2:2:N-2,:,3)+Ih(4:2:N,:,3))/2;
%Ih(1,:,3)=Ih(2,:,3);

%%fine B
%%

I=uint8(Ih);