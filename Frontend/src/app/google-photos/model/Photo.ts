import { SafeResourceUrl } from "@angular/platform-browser";

export interface Photo {
  id: number;
  filename: string;
  latitude: number;
  longtitude: number;
  creationDate: Date;
  favorite: boolean;
  size: number;
  imgUrl?: SafeResourceUrl;
}
