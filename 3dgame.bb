 Graphics3D 400,300,32,2
SetBuffer BackBuffer()
button=LoadSound("button.wav")
click=LoadSound("click.wav")
Global resx,resy
splashmusic=LoadSound("splash.wav")
worldchn=PlaySound(splashmusic)
 resolution=LoadImage("resolution.bmp")
whitebox=LoadImage("whitebox.png")

;setup resolution
ShowPointer()
.getgfx
If Not ChannelPlaying(worldchn) Then 
play=1
If play=1 Then 
worldchn=PlaySound(splashmusic)
play=0
EndIf
EndIf  

If Not okhit=1 Then
Cls
If MouseY()>120 And MouseY()<140 Then DrawImage whitebox,0,120
If MouseDown(1) And MouseY()>120 And MouseY()<140 Then scrolladj=5 Resx=1024 resy=768 okhit=1 PlaySound(click)
If MouseY()>150 And MouseY()<170 Then DrawImage whitebox,0,150
If MouseDown(1) And MouseY()>150 And MouseY()<170 Then scrolladj=7 Resx=1440 resy=900 okhit=1 PlaySound(click)
If MouseY()>180 And MouseY()<200 Then DrawImage whitebox,0,180
If MouseDown(1) And MouseY()>180 And MouseY()<200 Then scrolladj=8 Resx=1600 resy=900 okhit=1PlaySound(click)
If MouseY()>214 And MouseY()<234 Then DrawImage whitebox,0,214
If MouseDown(1) And MouseY()>214 And MouseY()<234 Then scrolladj= 9 Resx=1920 resy=1080 okhit=1PlaySound(click)
DrawImage resolution,0,0
EndIf 
Flip
If Not okhit=1 Then Goto getgfx
StopChannel(worldchn)





SeedRnd MilliSecs()
;;;;;;;;;;;;;;;;camera;;;;;;;;;;;;;;;;;;;;;;;;;;;;
Graphics3D resx,resy,32,1
SetBuffer BackBuffer()



;peripheral lights
light=CreateLight(3) 
sunspot=CreateLight(3)
LightConeAngles sunspot,0,15
PositionEntity sunspot,0,500,0
;declare
red=150
blue=250
green=150

;create camera
camera=CreateCamera()
CameraViewport camera,0,0,resx,resy
PositionEntity camera,0,5,1.5
CameraClsColor camera,150,150,255
CameraRange camera,.1,100000

;secondary camera

;collisions
Collisions 2,3,2,1
Collisions 1,2,2,1
Collisions 2,2,2,1

;;;loadsound;;;;
lazersound=LoadSound("laser.wav")


;create ground
ground=CreatePlane()
groundtexture=LoadTexture("grass1.png")
ScaleTexture groundtexture,5,5
EntityTexture ground,groundtexture
;create tower
tower= CreateCylinder(32)
ScaleEntity tower,1,3,1
PositionEntity tower,0,1.5,0
towertexture=LoadTexture("wood.jpg")
ScaleTexture towertexture,.2,.2
EntityTexture tower,towertexture
;turret part
turret = CreateSphere(16)
turrettexture=LoadTexture("turret.jpg")
ScaleTexture turrettexture,1,2
PositionTexture turrettexture,.5,1
EntityTexture turret,turrettexture
EntityShininess turret,.5
PositionEntity turret,0,5,0

;cannon part
cannon=CreateCylinder(16)
cannontexture=LoadTexture("cannon.bmp")
EntityTexture cannon,cannontexture
PositionEntity cannon,0,5,3
ScaleEntity cannon,.2,2,.2
EntityShininess cannon,1
RotateEntity cannon,90,0,0
EntityParent cannon,turret 
EntityParent camera,cannon



;createsun
sun=CreateSphere(32)
ScaleEntity sun,500,500,500
suntex=LoadTexture("sun.png")


;mini towers
Type minitower
Field exist 
Field power
Field hp
Field speed
End Type 

;Lazerbeam 
Type lazerbeam
Field speed
Field power
Field exist
Field distance
Field light
End Type 

;enemy balls
Type deathball
Field size#
Field hp
Field exist
Field id
Field degrees#
Field distance#
End Type 

speed=1
power=1
armor=1
maxhp=10
hp=10

;towerlight
towerlight=CreateLight(2)
PositionEntity towerlight,0,10,3
RotateEntity towerlight,90,0,0 
EntityParent towerlight,cannon
LightRange towerlight,1
EntityParent towerlight,cannon
balltex=LoadTexture("ball.png")

runngun=LoadSound("Lucid Creation - Run'n'Gun.mp3")
lost=LoadSound("Lucid Creation -  Lost.mp3")
tube=LoadSound("Lucid Creation - Tube")
bossmusic=LoadSound("boss.mp3")

;sounds
bang=LoadSound("bang.wav")
hammer=LoadSound("hammer.wav")

;create clock
clock=LoadImage("clock.png")
MidHandle clock


timer=CreateTimer(60)
sunlight=1

level=1
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;Main loop::::::::::::::::::::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
  While Not KeyHit(1)
If Not ChannelPlaying(worldchn) Then
play=1
EndIf 
If play=1 And sunlight>0 Then
song=Rand(1,3)
If song=1 Then worldchn=PlaySound(runngun)
If song=2 Then worldchn=PlaySound(lost)
If song=3 Then worldchn=PlaySound(tube)
play=0
EndIf 
If sunlight<0 Then StopChannel(worldchn)



;gamelighting
PointEntity sunspot,sun
PointEntity light,tower
sunmove#=sunmove#+.025
If sunx#<-100 And allclear=1 And KeyDown(57) Then sunmove#=sunmove#+1
If sunmove#=360 Then sunmove#=0
sunx#=10000*Cos(sunmove#)
suny#=10000*Sin(sunmove#)
PositionEntity sun,0,sunx#,suny#
PositionEntity light,0,sunx#,suny#
sunlight=sunx#/4

If sunlight>0 And sunlight<630 Then blue=(sunlight/5)*2
If sunlight>-130 And sunlight<630 Then green=sunlight/5+25
If sunlight>-250 And sunlight<505 Then red=sunlight/5+50
LightColor light,red,green,blue
LightColor sunspot,150,green,0
AmbientLight red,green,blue

CameraClsColor camera,red,green,blue
If sunlight<-250 Then LightRange sunspot,0 Else LightRange sunspot,10000
If sunlight<-250 Then LightRange light,0 Else LightRange light,10000
;daychange level up
If sunlight<0 And change=0 Then change=1
If sunlight>0 And change=1 Then level=level+1 change=0 enemies=level*5







scopepurchased=1

 If MouseX()<10 Or MouseX()>1000 Then
MoveMouse 500,250
EndIf
 If MouseY()<10 Or MouseY()>500 Then
MoveMouse 500,250
EndIf
If Not MouseDown(2) Then 
mxs#=mxs#-MouseXSpeed()*.1
mys#=mys#+MouseYSpeed()*.1
Else 
mxs#=mxs#-MouseXSpeed()*.03
mys#=mys#+MouseYSpeed()*.03
EndIf 
If mxs#<.1 Then mxs#=360
If mxs#>360 Then mxs=.1
If mys#>30 Then mys#=30
If mys#<-89 Then mys#=-89
RotateEntity turret,mys#,mxs#,0
 



;; down to business

;fire cannon/create laser beam
If MouseHit(1) Then
ab.lazerbeam=New lazerbeam
ab\power=power
ab\speed=speed
ab\exist=CreateSphere(16)
EntityType ab\exist,1
PositionEntity ab\exist,0,5,0
ScaleEntity ab\exist,.1,.1,10
PlaySound lazersound
EntityColor ab\exist,power,255,255
RotateEntity ab\exist,mys#,mxs#,0
EndIf
 



;delete deathballs after lazer hits
For check.deathball=Each deathball
If check\size#<4 Then check\distance#=check\distance#-check\size#/4 Else check\distance#=check\distance#-check\size#/50
PositionEntity check\exist,check\distance#*Cos(check\degrees#),check\size#*2,check\distance#*Sin(check\degrees#)
TurnEntity check\exist,3.14,0,0
If CountCollisions(check\exist) Then
If EntityCollided(check\exist,1) Then
check\size#=check\size#-power
If check\size#>0 Then MoveEntity check\exist,0,-2,0
ScaleEntity check\exist,check\size#*2,check\size#*2,check\size#*2
If check\size#=0 Then
cash=cash+100
FreeEntity check\exist
Delete check
ballcount=ballcount-1
EndIf 
EndIf
EndIf 
Next
;towerhit
For check.deathball=Each deathball
If check\distance<1 Then 
hp=hp-check\size# 
FreeEntity check\exist
Delete check
ballcount=ballcount-1
PlaySound(bang)
EndIf  
Next

 

;move lazerbeam 
.lazerbeams
For ab.lazerbeam=Each lazerbeam
MoveEntity ab\exist,0,0,ab\speed
ab\distance=ab\distance+ab\speed   
If ab\distance>1000 Then 
FreeEntity ab\exist
Delete ab
Else 
If CountCollisions(ab\exist) Then FreeEntity ab\exist Delete ab
EndIf
Next 
;second wave for the day
If sunx#>9998 And makemore=0 Then
makemore=1
enemies=level*5
EndIf 
If sunx#<9998 Then makemore=0

;make boss
If level Mod 5 =0 And sunx#>0 And bosscreated=0 Then
StopChannel(worldchn)
worldchn=PlaySound(bossmusic)
newball.deathball=New deathball
ballcount=ballcount+1
size=50+level
newball\distance#=1000
newball\exist= CreateSphere(16)
EntityColor newball\exist,Rand(50,255),0,0
ScaleEntity newball\exist,size*2,size*2,size*2
EntityPickMode newball\exist,2
EntityType newball\exist,2 
newball\size= size
newball\degrees#=Rnd(.01,360)
PositionEntity newball\exist,1000*Cos(Newball\degrees#),size*2,1000*Sin(newball\degrees#)
PointEntity newball\exist,tower
bosscreated=1
enemies=0
EndIf 



;level setup
If enemies>0 Then 
For ballscreate=1 To enemies
newball.deathball=New deathball
ballcount=ballcount+1
size=Rand(1,3)
newball\distance#=1000
newball\exist= CreateSphere(16)
EntityColor newball\exist,Rand(50,255),0,0
ScaleEntity newball\exist,size*2,size*2,size*2
EntityPickMode newball\exist,2
EntityType newball\exist,2 
newball\size= size
newball\degrees#=Rnd(.01,360)
PositionEntity newball\exist,1000*Cos(Newball\degrees#),size*2,1000*Sin(newball\degrees#)
PointEntity newball\exist,tower
enemies=enemies-1
Next
EndIf

;scope view
If scopepurchased=1 And MouseDown(2) Then CameraZoom camera,3 Else CameraZoom camera,1



UpdateWorld
RenderWorld

;radar display
If radarpurchased=1 Then
Color 0,0,1
Oval GraphicsWidth()-100,GraphicsHeight()-100,100,100,1
Color 255,0,0
For all.deathball=Each deathball
dis=all\distance#/20
Oval GraphicsWidth()-50+dis*Cos(-all\degrees#),GraphicsHeight()-50+dis*Sin(-all\degrees#),all\size#,all\size#,1
Color 0,255,0
Line GraphicsWidth()-50,GraphicsHeight()-50,GraphicsWidth()-50+50*Cos(-mxs#-90),GraphicsHeight()-50+50*Sin(-mxs#-90)
Next
EndIf
;crosshairs
If crosspurchase=1 Then
Color 255,255,0
Line GraphicsWidth()/2-10,GraphicsHeight()/2,GraphicsWidth()/2,GraphicsHeight()/2-10
Line GraphicsWidth()/2-10,GraphicsHeight()/2,GraphicsWidth()/2,GraphicsHeight()/2+10
Line GraphicsWidth()/2,GraphicsHeight()/2-10,GraphicsWidth()/2+10,GraphicsHeight()/2
Line GraphicsWidth()/2+10,GraphicsHeight()/2,GraphicsWidth()/2,GraphicsHeight()/2+10
Plot GraphicsWidth()/2,GraphicsHeight()/2
cameracheck= CameraPick (camera,GraphicsWidth()/2,GraphicsHeight()/2)
If cameracheck>0 And radarpurchased=1 Then
Color 100,255,100
Line GraphicsWidth()/2-5,GraphicsHeight()/2,GraphicsWidth()/2,GraphicsHeight()/2-5
Line GraphicsWidth()/2-5,GraphicsHeight()/2,GraphicsWidth()/2,GraphicsHeight()/2+5
Line GraphicsWidth()/2,GraphicsHeight()/2-5,GraphicsWidth()/2+5,GraphicsHeight()/2
Line GraphicsWidth()/2+5,GraphicsHeight()/2,GraphicsWidth()/2,GraphicsHeight()/2+5
Plot GraphicsWidth()/2,GraphicsHeight()/2
EndIf
EndIf

;HP BAR
Color 1,1,1
Rect 5,10,GraphicsWidth()-10,12
Color 255,0,0
Rect 20,14,(GraphicsWidth()-40)/maxhp*hp,4
Color 0,255,0
Text 6,10, "HP"
Text 6,30, "$"+cash

;quiet music as night approaches
If sunlight>500 Then daytime=1
If sunlight<0 Then daytime=0 
If sunlight<500 And daytime=1 Then 
If sunlight=499 Then musicvol#=1
If sunlight=450 Then musicvol#=.9
If sunlight=400 Then musicvol#=.8
If sunlight=350 Then musicvol#=.7
If sunlight=300 Then musicvol#=.6
If sunlight=250 Then musicvol#=.5
If sunlight=200 Then musicvol#=.4
If sunlight=150 Then musicvol#=.3
If sunlight=100 Then musicvol#=.2
If sunlight=50 Then musicvol#=.1

ChannelVolume worldchn,musicvol#
EndIf 
If sunlight>1 And daytime=0 Then ChannelVolume worldchn,1



;allclear
If sunx#<0 And ballcount=0 Then
bosscreated=0
allclear=1
Text 10,GraphicsHeight()-20,"ALL CLEAR! You may now press F1 to enter the shop, or hold spacebar to speed to next wave."
Else allclear=0
EndIf 
;start shop
If KeyHit(59) Then
shop=shop+1
If shop>1 Then shop=0
EndIf 


If shop=1 And allclear=1 Then 
Color 255,255,255
Text 30,40, "1: $3000 to purchase crosshair upgrade."
Text 30,60, "2: $5000 to purchase Radar."
Text 30,80, "3: $500 to purchase Max HP+1."
Text 30,100, "4: $100 to purchase HP."
Text 30,120,"5: $10,000 to purchase cannon power upgrade."
Text 30,140,"6: $10,000 to purchase cannon shot-speed upgrade."
Text 30,160,"7: $2,500 to purchase scope-view.(rightclick)
Color 255,0,0 
If scopepurchased=0 Then Text GraphicsWidth()-500,160,"Not Purchased"
If crosspurchase=0 Then Text GraphicsWidth()-500,40,"Not Purchased"
If radarpurchased=0 Then Text GraphicsWidth()-500,60,"Not Purchased"
Text GraphicsWidth()-500,80, "Current maxHP: "+maxhp
Text GraphicsWidth()-500,100, "Current HP   : "+hp
Text GraphicsWidth()-500,120,"Current Power: "+power
Text GraphicsWidth()-500,140,"Current Speed: "+speed
If cash>2999 And KeyHit(2) And crosspurchase=0 Then crosspurchase=1 cash=cash-3000 PlaySound(hammer)
If cash>4999 And KeyHit(3) And radarpurchased=0 Then radarpurchased=1 cash=cash-5000 PlaySound(hammer)
If cash>499 And KeyHit(4) Then maxhp=maxhp+1 PlaySound(hammer) cash=cash-500
If cash>99 And KeyHit(5) And hp<maxhp Then hp=hp+1 PlaySound(click) cash=cash-100
If cash>9900 And KeyHit(6) Then power=power+1 PlaySound(hammer) cash=cash-10000
If cash>9900 And KeyHit(7) Then speed=speed+1 PlaySound(hammer) cash=cash-10000
If cash>2400 And KeyHit(8) Then scopepurchased=1 PlaySound(hammer) cash=cash-2500
Text 50,600, "Press F1 to return."
EndIf  

If hp<1 Then Goto gameover
;dayclock
DrawImage clock,GraphicsWidth()-60,100
For a#=1 To sunmove#
Color 255,255,0
Line GraphicsWidth()-60,100,GraphicsWidth()-60+45*Sin(-sunmove#-180),100+45*Cos(-sunmove#-180)
Next

Text 60,60,"available vid mem :" + AvailVidMem() 

VWait 
Flip
  Wend
  End
.createtower
ba.minitower=New minitower
ba\hp=10
ba\speed=towerspeed
ba\power=towerpower
ba\exist=CreateCylinder(8)
ScaleEntity ba\exist,1,3,1
PositionEntity ba\exist,0,5,3
PositionEntity camera,0,20,20
.placetower
PointEntity camera,ba\exist
If KeyDown(17) Then MoveEntity ba\exist,0,0,.01
If KeyDown(30) Then MoveEntity ba\exist,-.01,0,0
If KeyDown(32) Then MoveEntity ba\exist,.01,0,0
If KeyDown(31) Then MoveEntity ba\exist,0,0,-.01

RenderWorld
Flip 
If MouseHit(1) Then Return 
Goto placetower


.gameover
StopChannel(worldchn) 
Cls
fntArial=LoadFont("Arial",100)
SetFont fntArial 
.gameover2
Cls
Text GraphicsWidth()/2,GraphicsHeight()/2,"You are dead",True,True
If KeyHit(1) Then End
Flip
Goto gameover2