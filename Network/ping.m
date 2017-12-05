filename = 'Ping-Table.csv';
filename1 = 'Ping-Table-output.csv';
%[data,txt,raw] = xlsread(filename,'C2:C13');
table = readtable(filename);
txt = table2array(table(3:13,3));
M = zeros(length(txt),10);
for i=1:length(txt),
    domain = txt(i);
    command = strcat("ping -c 5 -s 64 ",domain);
    [status,cmdout] = system(command);
    s = strsplit(cmdout,'\n');
    round_trip = s(length(s)-1);
    round_trip = strsplit(char(round_trip),{'/',' '});
    min = round_trip(7);
    avg = round_trip(8);
    max = round_trip(9);
    stddev = round_trip(10);
    cmdout_test = s(2);
    ttl = strsplit(char(cmdout_test),{'=',' '});
    ttl = ttl(8);
    packets = s(length(s)-2);
    ploss = strsplit(char(packets),{', ','%'});
    ploss = ploss(length(ploss)-1);
    options = strsplit(command,' ');
    C = options(3);
    DF = 0;
    S = options(5);
    %command = strcat("traceroute ",domain);
    %[status,cmdout] = system(command);
    %s = strsplit(cmdout,'\n');
    %hops = s(1);
    %hops = strsplit(char(hops),{' ',', '});
    %hops = hops(5)
    hops = 0;
    %write to xls: C,DF,S,ttl,min,max,avg,stddev,ploss,hops
    %start from colD,...
    M(i,:) = [C DF S ttl min max avg stddev ploss hops];
    
end;

csvwrite(filename1,M);