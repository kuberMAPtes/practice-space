import {useEffect} from "react";

export default function Maps() {
    useEffect(() => {
        const mapDiv = document.getElementById("map");
        const map = new window.naver.maps.Map(mapDiv);
    }, []);

    return (
        <div>
            <div id="map" style={{width: "400px", height: "400px"}} />
        </div>
    );
}
