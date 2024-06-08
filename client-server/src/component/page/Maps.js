import {useEffect, useState} from "react";

export default function Maps() {
    const [txt, setTxt] = useState();

    navigator.geolocation.getCurrentPosition(
        (position) => {
            setTxt("Latitude: " + position.coords.latitude + "\nLongitude: " + position.coords.longitude);
        },
        () => {
            setTxt("Geolocation is not available");
        }
    )

    useEffect(() => {}, []);

    return (
        <div>{txt}</div>
    );
}
