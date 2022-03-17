import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { ImportService } from '../../services/import.service';

@Component({
  selector: 'import-page',
  templateUrl: './import-page.component.html',
  styleUrls: ['./import-page.component.scss'],
})
export class ImportPageComponent implements OnInit {
  fileToUpload: File | any = null;
  invalidFile: boolean = false;

  constructor(
    public dialogRef: MatDialogRef<ImportPageComponent>,
    private importService: ImportService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void {}

  import() {
    if (this.fileToUpload != null) {
      const formData = new FormData();
      formData.append('file', this.fileToUpload, this.fileToUpload.name);

      this.importService.sendImportData(formData).subscribe(
        (data) => {
          this.dialogRef.close();
          this.invalidFile = false;
          this.fileToUpload = null;
          this.router.navigate(['/contacts']).then((navigated) => {
            this.snackBar.open('Your contacts have been uploaded.', 'Close', {
              duration: 3000,
            });
          });
        },
        (err) => {
          this.invalidFile = true;
        }
      );
    }
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }
}
