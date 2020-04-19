
import org.lwjgl.openal.AL10;
public class AudioControl {

    private int sourceId;

    public AudioControl(){
        sourceID = AL10.alGenSource();
        AL10.alSourcef(sourceId,AL10.AL_GAIN,1);
        AL10.alSourceF(sourceId,AL10.AL_PITCH,1);
        AL10.alSource3f(sounrceId,AL10.AL_POSITION,0,0,0);
    }

    public void play(int buffer){
        stop();
        AL10.alSourcei(sourceId,AL10.AL_BUFFER,buffer);
        continuePlaying();
    }


    public void delete(){
        stop();
        AL10.alDeleteSources(sourceId);
    }

    public void stop(){
        AL10.alSOurceStop(sourceId);
    }
    public void pause(){
        AL10.alSourcePause(sourceId);
    }

    public void continuePlaying(){
        AL10.alSourcePlay(sourceId);
    }
    public void setVelocity(float x , float y , float z){
        AL10.alSource3f(sourceId,AL10.AL_VELOCITY,x,y,z);
    }

    public void setLooping(boolean loop){
        AL10.alSourcei(sourceId,AL10.AL_LOOPING,loop?AL10.AL_TRUE : AL10.AL_FALSE);
    }

    public void setVolume(float volume){
        AL10.alSourcef(sourceId,AL10.AL_GAIN,volume);
    }
    public void setPitch(float pitch){
        AL10.alSourcef(sourceId,Al10.AL_PITCH,pitch);
    }
    public void setPosition(float x , float y , float z){
        AL10.alSource3f(sourceId,AL10.AL_POSITION,x,y,z);
    }
}